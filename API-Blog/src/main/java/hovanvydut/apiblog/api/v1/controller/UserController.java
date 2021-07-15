package hovanvydut.apiblog.api.v1.controller;

import hovanvydut.apiblog.api.v1.request.CreateUserReq;
import hovanvydut.apiblog.api.v1.request.UpdateUserReq;
import hovanvydut.apiblog.api.v1.response.UserPageResp;
import hovanvydut.apiblog.api.v1.response.UserResp;
import hovanvydut.apiblog.common.constant.PagingConstant;
import hovanvydut.apiblog.core.user.UserService;
import hovanvydut.apiblog.core.user.dto.CreateUserDTO;
import hovanvydut.apiblog.core.user.dto.UpdateUserDTO;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public ResponseEntity<UserPageResp> getAllUsers(@RequestParam(required = false) Optional<String> keyword,
                                                    @RequestParam(required = false) Optional<Integer> page,
                                                    @RequestParam(required = false) Optional<Integer> size,
                                                    @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

        Page<UserDTO> pageUserDTO = this.userService.getUsers(page.orElse(1),
                size.orElse(PagingConstant.USERS_PER_PAGE), sort, keyword.orElse(""));

        return ResponseEntity.ok(this.modelMapper.map(pageUserDTO, UserPageResp.class));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserResp> getUser(@PathVariable String username) {
        UserDTO userDTO = this.userService.getUserByUsername(username);
        return ResponseEntity.ok(this.modelMapper.map(userDTO, UserResp.class));
    }

    @GetMapping("/users/{username}/articles")
    public void getArticlesOfUser(@PathVariable String username,
                                  @RequestParam(required = false) Optional<String> keyword,
                                  @RequestParam(required = false) Optional<Integer> page,
                                  @RequestParam(required = false) Optional<Integer> size,
                                  @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {
        // get all published article of user
    }

    @GetMapping("/users/{username}/series")
    public void getSeriesOfUser(@PathVariable String username,
                                @RequestParam(required = false) Optional<String> keyword,
                                @RequestParam(required = false) Optional<Integer> page,
                                @RequestParam(required = false) Optional<Integer> size,
                                @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

    }

    @GetMapping("/users/{username}/following-users")
    public void getFollowingOfUser(@PathVariable String username,
                                   @RequestParam(required = false) Optional<String> keyword,
                                   @RequestParam(required = false) Optional<Integer> page,
                                   @RequestParam(required = false) Optional<Integer> size,
                                   @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

    }

    @GetMapping("/users/{username}/followers")
    public void getFollowersOfUser(@PathVariable String username,
                                   @RequestParam(required = false) Optional<String> keyword,
                                   @RequestParam(required = false) Optional<Integer> page,
                                   @RequestParam(required = false) Optional<Integer> size,
                                   @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

    }

    @GetMapping("/users/{username}/following-tags")
    public void getFollowingTagsOfuser(@PathVariable String username,
                                   @RequestParam(required = false) Optional<String> keyword,
                                   @RequestParam(required = false) Optional<Integer> page,
                                   @RequestParam(required = false) Optional<Integer> size,
                                   @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

    }



    @GetMapping("/users/{username}/hovercard")
    public void getHoverCard(@PathVariable String username) {

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<UserResp> createNewUser(@Valid @RequestBody CreateUserReq req) {
        CreateUserDTO dto = this.modelMapper.map(req, CreateUserDTO.class);
        UserDTO userDTO = this.userService.createUser(dto, true);

        return ResponseEntity.ok(this.modelMapper.map(userDTO, UserResp.class));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users/{username}")
    public ResponseEntity<UserResp> updateUser(@PathVariable String username, @Valid @RequestBody UpdateUserReq req) {
        UpdateUserDTO dto = this.modelMapper.map(req, UpdateUserDTO.class);
        UserDTO userDTO = this.userService.updateUser(username, dto);

        return ResponseEntity.ok(this.modelMapper.map(userDTO, UserResp.class));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username) {
        this.userService.deleteUser(username);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR') and #username != authentication.principal.username")
    @PostMapping("/users/{username}/banned")
    public void bannedUser(@PathVariable String username, Principal principal) {
        System.out.println(principal);
        System.out.println("Test hasRoleAny");
    }

}
