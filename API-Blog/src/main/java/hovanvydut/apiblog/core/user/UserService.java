package hovanvydut.apiblog.core.user;

import hovanvydut.apiblog.common.exception.MyError;
import hovanvydut.apiblog.core.auth.dto.CreateUserRegistrationDTO;
import hovanvydut.apiblog.core.user.dto.CreateUserDTO;
import hovanvydut.apiblog.core.user.dto.ResetPasswordDto;
import hovanvydut.apiblog.core.user.dto.UpdateUserDTO;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.util.List;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

public interface UserService {
    Page<UserDTO> getUsers(int page, int size, String[] sort, String searchKeyword);
    UserDTO getUserByEmailOrUsername(String email, String username);
    UserDTO getUserByEmail(String email);
    UserDTO getUserByUsername(String username);
    UserDTO createUser(@Valid CreateUserDTO dto, boolean needHashPassword);
    UserDTO updateUser(String username, UpdateUserDTO dto);
    void deleteUser(String username);
    void followingUser(String fromUsername, String toUsername);
    void unFollowingUser(String fromUsername, String toUsername);
    boolean isEmailExist(String email);
    boolean isUsernameExist(String username);
    String registerNewUser(CreateUserRegistrationDTO dto);
    void confirmRegistration(String token);
    void declineRegistration(String token);
    void sendConfirmationEmail(String email);
    List<MyError> checkUnique(String email, String username);
    void changePassword(ResetPasswordDto dto, String username);
    void forgotPassword(String email);
    void resetForgotPassword(String token, String newPassword);
}
