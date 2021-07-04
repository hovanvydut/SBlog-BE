package hovanvydut.apiblog.core.user.service;

import hovanvydut.apiblog.core.user.dto.CreateUserDTO;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import hovanvydut.apiblog.core.user.repository.UserRepository;
import hovanvydut.apiblog.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
    public UserDTO createUser(@Valid CreateUserDTO dto, boolean hashPassword) {
        User user = this.modelMapper.map(dto, User.class);

        if (hashPassword) {
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        }

        this.userRepository.save(user);

        return this.modelMapper.map(user, UserDTO.class);
    }
}
