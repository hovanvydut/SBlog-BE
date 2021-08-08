package hovanvydut.apiblog.core.auth;

import hovanvydut.apiblog.core.auth.dto.CreateUserRegistrationDTO;
import hovanvydut.apiblog.core.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Validated
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    @Transactional
    public void createUserRegistration(@Valid CreateUserRegistrationDTO dto) {
        this.userService.registerNewUser(dto);
    }

    @Override
    @Transactional
    public void acceptRegistration(String token) {
        this.userService.confirmRegistration(token);
    }

    @Override
    @Transactional
    public void declineRegistration(String token) {
        this.userService.declineRegistration(token);
    }

    @Override
    public void resendConfirmationRegistrationEmail(String email) {
        this.userService.sendConfirmationEmail(email);
    }

}
