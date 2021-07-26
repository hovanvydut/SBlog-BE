package hovanvydut.apiblog.core.auth;

import hovanvydut.apiblog.core.auth.dto.CreateUserRegistrationDTO;
import hovanvydut.apiblog.core.mail.EmailService;
import hovanvydut.apiblog.core.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthServiceImpl(ModelMapper modelMapper,
                           UserService userService,
                           PasswordEncoder passwordEncoder,
                           EmailService emailService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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
