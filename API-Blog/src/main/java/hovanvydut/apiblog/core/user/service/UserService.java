package hovanvydut.apiblog.core.user.service;

import hovanvydut.apiblog.core.user.dto.CreateUserDTO;
import hovanvydut.apiblog.core.user.dto.UserDTO;

import javax.validation.Valid;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

public interface UserService {
    public UserDTO getUserByEmailOrUsername(String email, String username);
    public UserDTO getUserByEmail(String email);
    public UserDTO getUserByUsername(String username);
    public UserDTO createUser(@Valid CreateUserDTO dto, boolean hashPassword);
}
