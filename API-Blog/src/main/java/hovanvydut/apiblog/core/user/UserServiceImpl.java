package hovanvydut.apiblog.core.user;

import hovanvydut.apiblog.common.exception.MyError;
import hovanvydut.apiblog.common.exception.MyRuntimeException;
import hovanvydut.apiblog.common.exception.MyUsernameNotFoundException;
import hovanvydut.apiblog.common.util.SortAndPaginationUtil;
import hovanvydut.apiblog.core.auth.UserRegistrationRepository;
import hovanvydut.apiblog.core.tag.dto.TagDTO;
import hovanvydut.apiblog.core.user.dto.CreateUserDTO;
import hovanvydut.apiblog.core.user.dto.UpdateUserDTO;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import hovanvydut.apiblog.model.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
        return null;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return null;
    }

    @Override
    public UserDTO createUser(@Valid CreateUserDTO dto, boolean needHashPassword) {
        // check username, email is unique on both User table and UserRegistration table
        List<MyError> errorList = this.checkUnique(dto.getEmail(), dto.getUsername());
        System.out.println(errorList.size());
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

    @Override
    public List<MyError> checkUnique(String email, String username) {
        // check email, username unique in two table: user and userregistration
        List<MyError> errorList = new ArrayList<>();

        UserRegistration existEmailRegistration = this.userRegistrationRepo.findByEmail(email);
        if (existEmailRegistration == null) {
            User existEmailUser = this.userRepository.findByEmail(email);
            if (existEmailUser != null) {
                errorList.add(new MyError().setSource("email").setMessage("The email has already been taken"));
            }
        } else {
            errorList.add(new MyError().setSource("email").setMessage("The email has already been taken"));
        }

        UserRegistration existUsernameRegistration = this.userRegistrationRepo.findByUsername(username);
        if (existUsernameRegistration == null) {
            User existUsernameUser = this.userRepository.findByUsername(username);
            if (existUsernameUser != null) {
                errorList.add(new MyError().setSource("username").setMessage("The username has already been taken"));
            }
        } else {
            errorList.add(new MyError().setSource("username").setMessage("The username has already been taken"));
        }

        return errorList;
    }

    @Override
    public UserDTO updateUser(String username, UpdateUserDTO dto) {
        User user = this.userRepository.findByUsername(username);

        if (user == null) {
            throw new MyUsernameNotFoundException(username);
        }

        this.modelMapper.map(dto, user);

        User savedUser = this.userRepository.save(user);

        return this.modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(String username) {
        User user = this.userRepository.findByUsername(username);

        if (user == null) {
            throw new MyUsernameNotFoundException(username);
        }

        this.userRepository.delete(user);
    }

    @Override
    public void followingUser(String fromUsername, String toUsername) {
        User fromUser = this.userRepository.findByUsername(fromUsername);
        User toUser = this.userRepository.findByUsername(toUsername);

        if (fromUser == null) {
            throw new MyUsernameNotFoundException(fromUsername);
        }

        if (toUser == null) {
            throw new MyUsernameNotFoundException(toUsername);
        }

        FollowerId followerId = new FollowerId().setFromUserId(fromUser.getId()).setToUserId(toUser.getId());

        Follower follower = new Follower().setId(followerId).setFromUser(fromUser).setToUser(toUser);

        this.followerRepository.save(follower);
    }

    @Override
    public void unFollowingUser(String fromUsername, String toUsername) {
        User fromUser = this.userRepository.findByUsername(fromUsername);
        User toUser = this.userRepository.findByUsername(toUsername);

        if (fromUser == null) {
            throw new MyUsernameNotFoundException(fromUsername);
        }

        if (toUser == null) {
            throw new MyUsernameNotFoundException(toUsername);
        }

        String msgError = "User with username = " + fromUsername + " is not following User with username = " + toUsername;

        FollowerId followerId = new FollowerId().setFromUserId(fromUser.getId()).setToUserId(toUser.getId());

        Follower follower = this.followerRepository.findById(followerId)
                .orElseThrow(() -> new MyRuntimeException(List.of(new MyError().setMessage(msgError))));

        this.followerRepository.delete(follower);
    }

}
