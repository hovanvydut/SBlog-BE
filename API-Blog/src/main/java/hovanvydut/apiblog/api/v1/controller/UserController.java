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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<UserPageResp> getAllUsers(@RequestParam(required = false) Optional<String> keyword,
                                                    @RequestParam(required = false) Optional<Integer> page,
                                                    @RequestParam(required = false) Optional<Integer> size,
                                                    @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

        Page<UserDTO> pageUserDTO = this.userService.getUsers(page.orElse(1),
                size.orElse(PagingConstant.USERS_PER_PAGE), sort, keyword.orElse(""));

        return ResponseEntity.ok(this.modelMapper.map(pageUserDTO, UserPageResp.class));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResp> getUser(@PathVariable String username) {
        UserDTO userDTO = this.userService.getUserByUsername(username);
        return ResponseEntity.ok(this.modelMapper.map(userDTO, UserResp.class));
    }

    @GetMapping("/{username}/articles")
    public void getArticlesOfUser(@PathVariable String username,
                                  @RequestParam(required = false) Optional<String> keyword,
                                  @RequestParam(required = false) Optional<Integer> page,
                                  @RequestParam(required = false) Optional<Integer> size,
                                  @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

    }

    @GetMapping("/{username}/series")
    public void getSeriesOfUser(@PathVariable String username,
                                @RequestParam(required = false) Optional<String> keyword,
                                @RequestParam(required = false) Optional<Integer> page,
                                @RequestParam(required = false) Optional<Integer> size,
                                @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

    }

    @GetMapping("/{username}/clipped-articles")
    public void getClippedArticlesOfUser(@PathVariable String username,
                                @RequestParam(required = false) Optional<String> keyword,
                                @RequestParam(required = false) Optional<Integer> page,
                                @RequestParam(required = false) Optional<Integer> size,
                                @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

    }

    @GetMapping("/{username}/following-users")
    public void getFollowingOfUser(@PathVariable String username,
                                   @RequestParam(required = false) Optional<String> keyword,
                                   @RequestParam(required = false) Optional<Integer> page,
                                   @RequestParam(required = false) Optional<Integer> size,
                                   @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

    }

    @GetMapping("/{username}/followers")
    public void getFollowersOfUser(@PathVariable String username,
                                   @RequestParam(required = false) Optional<String> keyword,
                                   @RequestParam(required = false) Optional<Integer> page,
                                   @RequestParam(required = false) Optional<Integer> size,
                                   @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

    }

    @GetMapping("/{username}/following-tags")
    public void getFollowingTagsOfuser(@PathVariable String username,
                                   @RequestParam(required = false) Optional<String> keyword,
                                   @RequestParam(required = false) Optional<Integer> page,
                                   @RequestParam(required = false) Optional<Integer> size,
                                   @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

    }



    @GetMapping("{username}/hovercard")
    public void getHoverCard(@PathVariable String username) {

    }

    @PostMapping()
    public ResponseEntity<UserResp> createNewUser(@Valid @RequestBody CreateUserReq req) {
        CreateUserDTO dto = this.modelMapper.map(req, CreateUserDTO.class);
        UserDTO userDTO = this.userService.createUser(dto, true);

        return ResponseEntity.ok(this.modelMapper.map(userDTO, UserResp.class));
    }

    @PatchMapping("/{username}")
    public ResponseEntity<UserResp> updateUser(@PathVariable String username, @Valid @RequestBody UpdateUserReq req) {
        UpdateUserDTO dto = this.modelMapper.map(req, UpdateUserDTO.class);
        UserDTO userDTO = this.userService.updateUser(username, dto);

        return ResponseEntity.ok(this.modelMapper.map(userDTO, UserResp.class));
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        this.userService.deleteUser(username);
    }

    @PostMapping("/{username}/banned")
    public void bannedUser() {

    }
}
