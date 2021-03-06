package com.debugbybrain.blog.api.v1.auth;

import com.debugbybrain.blog.api.v1.auth.dto.LoginReq;
import com.debugbybrain.blog.api.v1.auth.dto.RegistrationReq;
import com.debugbybrain.blog.api.v1.auth.dto.ResendConfirmationEmailReq;
import com.debugbybrain.blog.common.util.JwtTokenUtil;
import com.debugbybrain.blog.core.auth.AuthService;
import com.debugbybrain.blog.core.auth.dto.CreateUserRegistrationDTO;
import com.debugbybrain.blog.core.mail.EmailService;
import com.debugbybrain.blog.core.upload.UploadService;
import freemarker.template.TemplateException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controllers related authenticate operations (login, register, active account, ...)
 *
 * @author hovanvydut
 * Created on 7/4/21
 */

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthService authService;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final UploadService uploadService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil,
                          AuthService authService,
                          ModelMapper modelMapper,
                          EmailService emailService,
                          UploadService uploadService) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authService = authService;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.uploadService = uploadService;
    }

    /**
     * Register new user endpoint
     * @param req
     * @return
     */
    @PostMapping("/auth/register")
    public void register(@Valid @RequestBody RegistrationReq req) {
        CreateUserRegistrationDTO dto = this.modelMapper.map(req,CreateUserRegistrationDTO.class);
        this.authService.createUserRegistration(dto);
    }

    /**
     * Login endpoint
     * @param req
     * @return token - a string token contains info that helps authenticate user
     */
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginReq req) {
        String username = req.getUsername();
        String password = req.getPassword();

        try {
            Authentication authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = this.jwtTokenUtil.generateAccessToken(userDetails);

            return ResponseEntity.ok().header(
                    HttpHeaders.AUTHORIZATION,
                    token
            ).body(token);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    /**
     * Resend a confirmation email
     * @param email - example@gmail.com
     */
    @PostMapping("/auth/register/resend-confirmation-email")
    public void resendConfirmationRegistrationEmail(@Valid @RequestBody ResendConfirmationEmailReq req) {
        this.authService.resendConfirmationRegistrationEmail(req.getEmail());
    }

    /**
     *
     * @param token
     */
    @GetMapping("/auth/register/activation/{token}/accept")
    public void confirmEmailAddress(@PathVariable("token") String token) {
        this.authService.acceptRegistration(token);
    }

    /**
     *
     * @param token
     */
    @GetMapping("/auth/register/activation/{token}/decline")
    public void cancelEmailConfirmation(@PathVariable("token") String token) {
        this.authService.declineRegistration(token);
    }

    @GetMapping("/auth/check-healthy")
    public String checkHealthy() {
        this.emailService.sendSimpleMessage("hovanvydut@gmail.com", "Check Healthy", "Tes mail ne");
        return "Fine!";
    }

    /**
     * This below sections are examples
     *
     */

    @GetMapping("/check-healthy/thymeleaf")
    public String thymeleaf() throws TemplateException, MessagingException, IOException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", "H??? V??n Vy");
        templateModel.put("text", "Link k??ch ho???t t??i kho???n: <a href='https://google.com'>link</a>");
        templateModel.put("senderName", "Blog");

        this.emailService.sendThymeleafMail("hovanvydut@gmail.com", "Check Healthy", templateModel);

        return "Fine!";
    }

}
