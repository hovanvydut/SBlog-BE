package hovanvydut.apiblog.core.auth;

import hovanvydut.apiblog.core.auth.dto.CreateUserRegistrationDTO;
import hovanvydut.apiblog.core.auth.dto.UserRegistrationDTO;

import javax.validation.Valid;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

public interface UserRegistrationService {
    public UserRegistrationDTO getUserRegistrationById(Long id);
    public UserRegistrationDTO getUserRegistrationByEmail(String email);
    public UserRegistrationDTO getUserRegistrationByUsername(String username);
    public UserRegistrationDTO getUserRegistrationBy(String email, String username);
    public UserRegistrationDTO createUserRegistration(@Valid CreateUserRegistrationDTO dto);
    void acceptRegistration(String token);
    void declineRegistration(String token);
}
