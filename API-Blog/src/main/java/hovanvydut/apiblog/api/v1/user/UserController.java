package hovanvydut.apiblog.api.v1.user;

import hovanvydut.apiblog.api.v1.user.dto.*;
import hovanvydut.apiblog.core.upload.UploadService;
import hovanvydut.apiblog.core.user.UserService;
import hovanvydut.apiblog.core.user.dto.CreateUserDTO;
import hovanvydut.apiblog.core.user.dto.ResetPasswordDto;
import hovanvydut.apiblog.core.user.dto.UpdateUserDTO;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UploadService uploadService;

    public UserController(UserService userService, ModelMapper modelMapper, UploadService uploadService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.uploadService = uploadService;
    }

    @ApiOperation(value = "Get all information of users")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @GetMapping("/users")
    public ResponseEntity<UserPageResp> getAllUsers(@Valid UserPaginationParams params) {

        Page<UserDTO> pageUserDTO = this.userService.getUsers(params.getPage(),
                params.getSize(), params.getSort(), params.getKeyword());

        return ResponseEntity.ok(this.modelMapper.map(pageUserDTO, UserPageResp.class));
    }

    @ApiOperation(value = "Get a user by username")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @GetMapping("/users/{username}")
    public ResponseEntity<UserResp> getUser(@PathVariable String username) {
        UserDTO userDTO = this.userService.getUserByUsername(username);
        return ResponseEntity.ok(this.modelMapper.map(userDTO, UserResp.class));
    }

    @ApiOperation(value = "")
    @GetMapping("/users/{username}/series")
    public void getSeriesOfUser(@PathVariable String username, @Valid UserPaginationParams params) {

    }

    @ApiOperation(value = "")
    @GetMapping("/users/{username}/hovercard")
    public void getHoverCard(@PathVariable String username) {

    }

    @ApiOperation(value = "")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<UserResp> createNewUser(@Valid @RequestBody CreateUserReq req) {
        CreateUserDTO dto = this.modelMapper.map(req, CreateUserDTO.class);
        UserDTO userDTO = this.userService.createUser(dto, true);

        return ResponseEntity.ok(this.modelMapper.map(userDTO, UserResp.class));
    }

    @ApiOperation(value = "")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users/{username}")
    public ResponseEntity<UserResp> updateUser(@PathVariable String username, @Valid @RequestBody UpdateUserReq req) {
        UpdateUserDTO dto = this.modelMapper.map(req, UpdateUserDTO.class);
        UserDTO userDTO = this.userService.updateUser(username, dto);

        return ResponseEntity.ok(this.modelMapper.map(userDTO, UserResp.class));
    }

    @ApiOperation(value = "")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username) {
        this.userService.deleteUser(username);
    }

    @ApiOperation(value = "")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR') and #username != authentication.principal.username")
    @PostMapping("/users/{username}/banned")
    public void bannedUser(@PathVariable String username, Principal principal) {

    }

    @ApiOperation(value = "")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/me/reset-password")
    public void changeMyPassword(@RequestBody ResetPwdReq req, Principal principal) {
        ResetPasswordDto dto = this.modelMapper.map(req, ResetPasswordDto.class);
        String username = principal.getName();

        this.userService.changePassword(dto, username);
    }

    @ApiOperation(value = "")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/{username}/reset-password")
    public void changeUserPassword(@RequestBody ResetPwdReq req, @PathVariable String username) {
        ResetPasswordDto dto = this.modelMapper.map(req, ResetPasswordDto.class);

        this.userService.changePassword(dto, username);
    }

    @ApiOperation(value = "")
    @PostMapping("/users/forgot-password")
    public void forgotPassword(@Valid @RequestBody ForgotPasswordReq req) {
        this.userService.forgotPassword(req.getEmail());
    }

    @ApiOperation(value = "")
    @PostMapping("/users/forgot-password/reset")
    public void resetForgotPassword(@Valid @RequestBody ResetForgotPassword req) {
        this.userService.resetForgotPassword(req.getToken(), req.getNewPassword());
    }

    @ApiOperation(value = "Upload avatar image")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/me/avatar")
    public String uploadAvatar(@RequestParam("image") MultipartFile multipartFile, Principal principal) throws IOException {
        String ownerUsername = principal.getName();
        return this.userService.uploadAvatar(multipartFile, ownerUsername);
    }


}
