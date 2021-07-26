package hovanvydut.apiblog.core.user;

import hovanvydut.apiblog.common.exception.*;
import hovanvydut.apiblog.common.util.SortAndPaginationUtil;
import hovanvydut.apiblog.core.auth.dto.CreateUserRegistrationDTO;
import hovanvydut.apiblog.core.listeners.registration.OnConfirmRegistrationEmailEvent;
import hovanvydut.apiblog.core.listeners.registration.OnRegistrationCompleteEvent;
import hovanvydut.apiblog.core.user.dto.CreateUserDTO;
import hovanvydut.apiblog.core.user.dto.UpdateUserDTO;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import hovanvydut.apiblog.model.entity.Follower;
import hovanvydut.apiblog.model.entity.FollowerId;
import hovanvydut.apiblog.model.entity.User;
import hovanvydut.apiblog.model.entity.VerificationToken;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final FollowerRepository followerRepo;
    private final VerificationTokenRepository verifyTokenRepo;
    private final ApplicationEventPublisher eventPublisher;


    public UserServiceImpl(UserRepository userRepo,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           FollowerRepository followerRepo,
                           VerificationTokenRepository verifyTokenRepo,
                           ApplicationEventPublisher eventPublisher) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.followerRepo = followerRepo;
        this.verifyTokenRepo = verifyTokenRepo;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Page<UserDTO> getUsers(int page, int size, String[] sort, String searchKeyword) {
        if (page <= 0) {
            page = 1;
        }

        if (size <= 0) {
            // NOTE: use default constant
            size = 5;
        }

        Sort sortObj = SortAndPaginationUtil.processSort(sort);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);

        Page<User> pageUser;

        if (searchKeyword == null || searchKeyword.isBlank()) {
            pageUser = this.userRepo.findAll(pageable);
        } else {
            pageUser = this.userRepo.search(searchKeyword, pageable);
        }

        // mapping Tag list --> TagDTO list
        List<User> users = pageUser.getContent();
        List<UserDTO> userDTOs = this.modelMapper.map(users, new TypeToken<List<UserDTO>>() {}.getType());

        return new PageImpl<>(userDTOs, pageable, pageUser.getTotalElements());
    }

    @Override
    public UserDTO getUserByEmailOrUsername(String email, String username) {
        return null;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = this.userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Could not found user with email = '" + email + "'"));

        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = this.userRepo.findByUsername(username)
                .orElseThrow(() ->new MyUsernameNotFoundException(username));

        return this.modelMapper.map(user, UserDTO.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @Transactional
    public UserDTO createUser(@Valid CreateUserDTO dto, boolean needHashPassword) {
        // check username, email is unique on both User table and UserRegistration table
        List<MyError> errorList = this.checkUnique(dto.getEmail(), dto.getUsername());

        if (errorList.size() > 0) {
            throw new MyRuntimeException(errorList);
        }

        User user = this.modelMapper.map(dto, User.class);

        if (needHashPassword) {
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        }

        this.userRepo.save(user);

        return this.modelMapper.map(user, UserDTO.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @Transactional
    public UserDTO updateUser(String username, UpdateUserDTO dto) {
        User user = this.userRepo.findByUsername(username)
                .orElseThrow(() -> new MyUsernameNotFoundException(username));

        this.modelMapper.map(dto, user);

        User savedUser = this.userRepo.save(user);

        return this.modelMapper.map(savedUser, UserDTO.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @Transactional
    public void deleteUser(String username) {
        User user = this.userRepo.findByUsername(username)
                .orElseThrow(() -> new MyUsernameNotFoundException(username));

        this.userRepo.delete(user);
    }

    @Override
    @Transactional
    public void followingUser(String fromUsername, String toUsername) {
        User fromUser = this.userRepo.findByUsername(fromUsername).orElseThrow(() -> new MyUsernameNotFoundException(fromUsername));
        User toUser = this.userRepo.findByUsername(toUsername).orElseThrow(() -> new MyUsernameNotFoundException(toUsername));

        FollowerId followerId = new FollowerId().setFromUserId(fromUser.getId()).setToUserId(toUser.getId());

        Follower follower = new Follower().setId(followerId).setFromUser(fromUser).setToUser(toUser);

        this.followerRepo.save(follower);
    }

    @Override
    @Transactional
    public void unFollowingUser(String fromUsername, String toUsername) {
        User fromUser = this.userRepo.findByUsername(fromUsername).orElseThrow(() -> new MyUsernameNotFoundException(fromUsername));
        User toUser = this.userRepo.findByUsername(toUsername).orElseThrow(() -> new MyUsernameNotFoundException(toUsername));

        String msgError = "User with username = " + fromUsername + " is not following User with username = " + toUsername;

        FollowerId followerId = new FollowerId().setFromUserId(fromUser.getId()).setToUserId(toUser.getId());

        Follower follower = this.followerRepo.findById(followerId)
                .orElseThrow(() -> new MyRuntimeException(List.of(new MyError().setMessage(msgError))));

        this.followerRepo.delete(follower);
    }

    @Override
    public boolean isEmailExist(String email) {
        return this.userRepo.getUserIdByEmail(email).isPresent();
    }

    @Override
    public boolean isUsernameExist(String username) {
        return this.userRepo.getUserIdByUsername(username).isPresent();
    }

    @Override
    @Transactional
    public String registerNewUser(CreateUserRegistrationDTO dto) {
        List<MyError> errors = this.checkUnique(dto.getEmail(), dto.getUsername());

        if (errors.size() > 0) {
            throw new MyRuntimeException(errors);
        }

        User user = this.modelMapper.map(dto, User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        User savedUser = this.userRepo.save(user);

        VerificationToken verifyToken = new VerificationToken().setToken(generateToken()).setUser(savedUser);
        VerificationToken savedVerifyToken = this.verifyTokenRepo.save(verifyToken);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(savedUser, savedVerifyToken));

        return verifyToken.getToken();
    }

    // TODO: should use @Transactional here ???
    @Override
    public void confirmRegistration(String token) {
        VerificationToken verifyToken = this.verifyTokenRepo.findByToken(token)
                .orElseThrow(() -> new VerifyTokenNotFoundException(token));

        if (verifyToken.isExpire()) {
//            throw new VerifyTokenExpireException();
            throw new RuntimeException("Token is expired");
        }

        User user = verifyToken.getUser();
        user.setEnabled(true);

        this.userRepo.save(user);
        this.verifyTokenRepo.delete(verifyToken);

        eventPublisher.publishEvent(new OnConfirmRegistrationEmailEvent());
    }

    @Override
    public void declineRegistration(String token) {
        VerificationToken verifyToken = this.verifyTokenRepo.findByToken(token)
                .orElseThrow(() -> new VerifyTokenNotFoundException(token));

        this.verifyTokenRepo.delete(verifyToken);
    }

    @Override
    public void sendConfirmationEmail(String email) {
        User user = this.userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Couldn't find User with email = '" + email + "'"));

        if (user.isEnabled()) {
            // do nothing
        } else {
            VerificationToken token = this.verifyTokenRepo.findById(user.getId())
                    .orElseThrow(() -> new VerifyTokenNotFoundException(user.getId()));

            if (token.isExpire()) {
                token.setToken(generateToken())
                        .setCreatedAt(LocalDateTime.now());
                token = this.verifyTokenRepo.save(token);
            }

            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, token));
        }
    }

    @Override
    public List<MyError> checkUnique(String email, String username) {
        List<MyError> errorList = new ArrayList<>();

        final boolean isEmailExisting = this.isEmailExist(email);
        final boolean isUsernameExisting = this.isUsernameExist(username);

        if (isEmailExisting) {
            errorList.add(new MyError().setSource("email").setMessage("The email has already been taken"));
        }

        if (isUsernameExisting) {
            errorList.add(new MyError().setSource("username").setMessage("The username has already been taken"));
        }

        return errorList;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
