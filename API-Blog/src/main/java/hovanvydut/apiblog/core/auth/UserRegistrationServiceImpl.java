package hovanvydut.apiblog.core.auth;

import hovanvydut.apiblog.common.exception.MyError;
import hovanvydut.apiblog.common.exception.MyRuntimeException;
import hovanvydut.apiblog.common.exception.UserRegistrationNotFoundException;
import hovanvydut.apiblog.common.exception.UserRegistrationTokenNotFoundException;
import hovanvydut.apiblog.core.auth.dto.CreateUserRegistrationDTO;
import hovanvydut.apiblog.core.auth.dto.UserRegistrationDTO;
import hovanvydut.apiblog.core.user.dto.CreateUserDTO;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import hovanvydut.apiblog.core.user.service.UserService;
import hovanvydut.apiblog.model.entity.User;
import hovanvydut.apiblog.model.entity.UserRegistration;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Validated
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRegistrationRepository userRegistrationRepo;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationServiceImpl(UserRegistrationRepository userRegistrationRepo,
                                       ModelMapper modelMapper,
                                       UserService userService,
                                       PasswordEncoder passwordEncoder) {
        this.userRegistrationRepo = userRegistrationRepo;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserRegistrationDTO getUserRegistrationById(Long id) {
        UserRegistration userRegistration = this.userRegistrationRepo.findById(id)
                .orElseThrow(() -> new UserRegistrationNotFoundException(id));

        return this.modelMapper.map(userRegistration, UserRegistrationDTO.class);
    }

    @Override
    public UserRegistrationDTO getUserRegistrationByEmail(String email) {
        UserRegistration userRegistration = this.userRegistrationRepo.findByEmail(email);

        if (userRegistration == null) {
            throw new UserRegistrationNotFoundException(email, "");
        }

        return this.modelMapper.map(userRegistration, UserRegistrationDTO.class);
    }

    @Override
    public UserRegistrationDTO getUserRegistrationByUsername(String username) {
        UserRegistration userRegistration = this.userRegistrationRepo.findByUsername(username);

        if (userRegistration == null) {
            throw new UserRegistrationNotFoundException("", username);
        }

        return this.modelMapper.map(userRegistration, UserRegistrationDTO.class);
    }

    @Override
    public UserRegistrationDTO getUserRegistrationBy(String email, String username) {
        UserRegistration userRegistration = this.userRegistrationRepo.findByEmailOrUsername(email, username);

        if (userRegistration == null) {
            throw new UserRegistrationNotFoundException(email, username);
        }

        return this.modelMapper.map(userRegistration, UserRegistrationDTO.class);
    }

    @Override
    public UserRegistrationDTO createUserRegistration(@Valid CreateUserRegistrationDTO dto) {

        // check username, email is unique on both User table and UserRegistration table
        List<MyError> errorList = checkUnique(dto);
        if (errorList.size() > 0) {
            throw new MyRuntimeException(errorList);
        }

        // register new user and send confirmation email
        UserRegistration userRegistration = this.modelMapper.map(dto, UserRegistration.class);

        String token = (UUID.randomUUID()).toString();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expireAt = createdAt.plusDays(30);

        userRegistration.setRegistrationToken(token)
                .setCreatedAt(createdAt)
                .setExpireAt(expireAt)
                .setPassword(this.passwordEncoder.encode(userRegistration.getPassword()));

        UserRegistration saved = this.userRegistrationRepo.save(userRegistration);

        return this.modelMapper.map(saved, UserRegistrationDTO.class);
    }

    @Override
    public void acceptRegistration(String token) {
        UserRegistration userRegistration = this.userRegistrationRepo.findByRegistrationToken(token);

        if (userRegistration == null) {
            throw new UserRegistrationTokenNotFoundException(token);
        }

        User user = new User();
        this.modelMapper.typeMap(UserRegistration.class, User.class)
                .addMappings(mapper -> {
                    mapper.skip(User::setId);
                    mapper.skip(User::setCreatedAt);
                }).map(userRegistration, user);

        this.userService.createUser(this.modelMapper.map(user, CreateUserDTO.class), false);
        this.userRegistrationRepo.delete(userRegistration);
    }

    @Override
    public void declineRegistration(String token) {
        UserRegistration userRegistration = this.userRegistrationRepo.findByRegistrationToken(token);

        if (userRegistration == null) {
            throw new UserRegistrationTokenNotFoundException(token);
        }

        this.userRegistrationRepo.delete(userRegistration);
    }

    private List<MyError> checkUnique(CreateUserRegistrationDTO dto) {
        // check email, username unique in two table: user and userregistration
        List<MyError> errorList = new ArrayList<>();

        UserRegistration existEmailRegistration = this.userRegistrationRepo.findByEmail(dto.getEmail());
        if (existEmailRegistration == null) {
            UserDTO existEmailUser = this.userService.getUserByEmail(dto.getEmail());
            if (existEmailUser != null) {
                errorList.add(new MyError().setSource("email").setMessage("The email has already been taken"));
            }
        } else {
            errorList.add(new MyError().setSource("email").setMessage("The email has already been taken"));
        }

        UserRegistration existUsernameRegistration = this.userRegistrationRepo.findByUsername(dto.getUsername());
        if (existUsernameRegistration == null) {
            UserDTO existUsernameUser = this.userService.getUserByUsername(dto.getUsername());
            if (existUsernameUser != null) {
                errorList.add(new MyError().setSource("username").setMessage("The username has already been taken"));
            }
        } else {
            errorList.add(new MyError().setSource("username").setMessage("The username has already been taken"));
        }

        return errorList;
    }
}
