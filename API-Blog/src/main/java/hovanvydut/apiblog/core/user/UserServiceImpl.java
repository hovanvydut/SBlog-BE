package hovanvydut.apiblog.core.user;

import hovanvydut.apiblog.common.exception.MyError;
import hovanvydut.apiblog.common.exception.MyRuntimeException;
import hovanvydut.apiblog.common.exception.MyUsernameNotFoundException;
import hovanvydut.apiblog.common.exception.UserNotFoundException;
import hovanvydut.apiblog.common.util.SortAndPaginationUtil;
import hovanvydut.apiblog.core.auth.UserRegistrationRepository;
import hovanvydut.apiblog.core.user.dto.CreateUserDTO;
import hovanvydut.apiblog.core.user.dto.UpdateUserDTO;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import hovanvydut.apiblog.model.entity.Follower;
import hovanvydut.apiblog.model.entity.FollowerId;
import hovanvydut.apiblog.model.entity.User;
import hovanvydut.apiblog.model.entity.UserRegistration;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRegistrationRepository userRegistrationRepo;
    private final FollowerRepository followerRepository;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           UserRegistrationRepository userRegistrationRepo,
                           FollowerRepository followerRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRegistrationRepo = userRegistrationRepo;
        this.followerRepository = followerRepository;
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
            pageUser = this.userRepository.findAll(pageable);
        } else {
            pageUser = this.userRepository.search(searchKeyword, pageable);
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
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Could not found user with email = '" + email + "'"));

        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username)
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

        this.userRepository.save(user);

        return this.modelMapper.map(user, UserDTO.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @Transactional
    public UserDTO updateUser(String username, UpdateUserDTO dto) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new MyUsernameNotFoundException(username));

        this.modelMapper.map(dto, user);

        User savedUser = this.userRepository.save(user);

        return this.modelMapper.map(savedUser, UserDTO.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @Transactional
    public void deleteUser(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new MyUsernameNotFoundException(username));

        this.userRepository.delete(user);
    }

    @Override
    @Transactional
    public void followingUser(String fromUsername, String toUsername) {
        User fromUser = this.userRepository.findByUsername(fromUsername).orElseThrow(() -> new MyUsernameNotFoundException(fromUsername));
        User toUser = this.userRepository.findByUsername(toUsername).orElseThrow(() -> new MyUsernameNotFoundException(toUsername));

        FollowerId followerId = new FollowerId().setFromUserId(fromUser.getId()).setToUserId(toUser.getId());

        Follower follower = new Follower().setId(followerId).setFromUser(fromUser).setToUser(toUser);

        this.followerRepository.save(follower);
    }

    @Override
    @Transactional
    public void unFollowingUser(String fromUsername, String toUsername) {
        User fromUser = this.userRepository.findByUsername(fromUsername).orElseThrow(() -> new MyUsernameNotFoundException(fromUsername));
        User toUser = this.userRepository.findByUsername(toUsername).orElseThrow(() -> new MyUsernameNotFoundException(toUsername));

        String msgError = "User with username = " + fromUsername + " is not following User with username = " + toUsername;

        FollowerId followerId = new FollowerId().setFromUserId(fromUser.getId()).setToUserId(toUser.getId());

        Follower follower = this.followerRepository.findById(followerId)
                .orElseThrow(() -> new MyRuntimeException(List.of(new MyError().setMessage(msgError))));

        this.followerRepository.delete(follower);
    }

    @Override
    public List<MyError> checkUnique(String email, String username) {
        // check email, username unique in two table: user and userregistration
        List<MyError> errorList = new ArrayList<>();

        Optional<UserRegistration> existEmailRegistration = this.userRegistrationRepo.findByEmail(email);
        if (existEmailRegistration.isEmpty()) {
            this.userRepository.findByEmail(email).ifPresent(user -> {
                errorList.add(new MyError().setSource("email").setMessage("The email has already been taken"));
            });
        } else {
            errorList.add(new MyError().setSource("email").setMessage("The email has already been taken"));
        }

        Optional<UserRegistration> existUsernameRegistration = this.userRegistrationRepo.findByUsername(username);
        if (existUsernameRegistration.isEmpty()) {
            this.userRepository.findByUsername(username).ifPresent(user -> {
                errorList.add(new MyError().setSource("username").setMessage("The username has already been taken"));
            });
        } else {
            errorList.add(new MyError().setSource("username").setMessage("The username has already been taken"));
        }

        return errorList;
    }

}
