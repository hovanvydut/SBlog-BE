package hovanvydut.apiblog.core.auth;

import hovanvydut.apiblog.core.auth.dto.CreateUserRegistrationDTO;

import javax.validation.Valid;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

public interface AuthService {
    public void createUserRegistration(@Valid CreateUserRegistrationDTO dto);
    void acceptRegistration(String token);
    void declineRegistration(String token);
    void resendConfirmationRegistrationEmail(String email);
}
