package hovanvydut.apiblog.core.user;

import hovanvydut.apiblog.common.exception.MyError;
import hovanvydut.apiblog.core.user.dto.CreateUserDTO;
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
    public Page<UserDTO> getUsers(int page, int size, String[] sort, String searchKeyword);
    public UserDTO getUserByEmailOrUsername(String email, String username);
    public UserDTO getUserByEmail(String email);
    public UserDTO getUserByUsername(String username);
    public UserDTO createUser(@Valid CreateUserDTO dto, boolean needHashPassword);
    public List<MyError> checkUnique(String email, String username);
    public UserDTO updateUser(String username, UpdateUserDTO dto);
    public void deleteUser(String username);
    public void followingUser(String fromUsername, String toUsername);
    public void unFollowingUser(String fromUsername, String toUsername);
}
