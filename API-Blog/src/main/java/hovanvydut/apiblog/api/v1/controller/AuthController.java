package hovanvydut.apiblog.api.v1.controller;

import freemarker.template.TemplateException;
import hovanvydut.apiblog.api.v1.request.LoginReq;
import hovanvydut.apiblog.api.v1.request.RegistrationReq;
import hovanvydut.apiblog.common.util.JwtTokenUtil;
import hovanvydut.apiblog.core.auth.UserRegistrationService;
import hovanvydut.apiblog.core.auth.dto.CreateUserRegistrationDTO;
import hovanvydut.apiblog.core.auth.dto.UserRegistrationDTO;
import hovanvydut.apiblog.core.mail.EmailService;
import hovanvydut.apiblog.core.upload.UploadService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRegistrationService userRegistrationService;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final UploadService uploadService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil,
                          UserRegistrationService userRegistrationService,
                          ModelMapper modelMapper,
                          EmailService emailService,
                          UploadService uploadService) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRegistrationService = userRegistrationService;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.uploadService = uploadService;
    }

    @PostMapping("/login")
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

    @PostMapping("/register")
    public UserRegistrationDTO register(@Valid @RequestBody RegistrationReq req) {
        CreateUserRegistrationDTO dto = this.modelMapper.map(req,CreateUserRegistrationDTO.class);
        return this.userRegistrationService.createUserRegistration(dto);
    }

    @GetMapping("/register/activation/{token}/accept")
    public void confirmEmailAddress(@PathVariable("token") String token) {
        this.userRegistrationService.acceptRegistration(token);
    }

    @GetMapping("/register/activation/{token}/decline")
    public void cancelEmailConfirmation(@PathVariable("token") String token) {
        this.userRegistrationService.declineRegistration(token);
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/admin-site")
    public String testAuthen() {
        return "abc";
    }

    @GetMapping("/check-healthy")
    public String checkHealthy() {
        this.emailService.sendSimpleMessage("hovanvydut@gmail.com", "Check Healthy", "Tes mail ne");
        return "Fine!";
    }

    @GetMapping("/check-healthy/freemarker")
    public String freemarker() throws TemplateException, MessagingException, IOException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", "Hồ Văn Vy");
        templateModel.put("text", "Link kích hoạt tài khoản: <a href='https://google.com'>link</a>");
        templateModel.put("senderName", "Blog");

        this.emailService.sendMessageUsingFreemarkerTemplate("hovanvydut@gmail.com", "Check Healthy", templateModel);

        return "Fine!";
    }

    @GetMapping("/check-healthy/thymeleaf")
    public String thymeleaf() throws TemplateException, MessagingException, IOException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", "Hồ Văn Vy");
        templateModel.put("text", "Link kích hoạt tài khoản: <a href='https://google.com'>link</a>");
        templateModel.put("senderName", "Blog");

        this.emailService.sendMessageUsingThymeleafTemplate("hovanvydut@gmail.com", "Check Healthy", templateModel);

        return "Fine!";
    }

}
