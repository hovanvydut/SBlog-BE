package hovanvydut.apiblog.api.v1.folllowing;

import hovanvydut.apiblog.api.v1.user.dto.UserPaginationParams;
import hovanvydut.apiblog.core.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author hovanvydut
 * Created on 7/13/21
 */

@RestController
@RequestMapping("/api/v1")
public class FollowingController {

    private final UserService userService;

    public FollowingController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "")
    @GetMapping("/users/{username}/following-users")
    public void getFollowingOfUser(@PathVariable String username, @Valid UserPaginationParams params) {
//        return this.userService.getFollowingUsers(username, params.getPage(), params.getSize(), params.getSort(), params.getKeyword());
    }

    @ApiOperation(value = "")
    @GetMapping("/users/{username}/followers")
    public void getFollowersOfUser(@PathVariable String username, @Valid UserPaginationParams params) {

    }

    @ApiOperation(value = "")
    @GetMapping("/users/{username}/following-tags")
    public void getFollowingTagsOfuser(@PathVariable String username, @Valid UserPaginationParams params) {

    }

    @ApiOperation(value = "")
    @PreAuthorize("isAuthenticated() and authentication.principal.username != #username")
    @PutMapping("/me/following/users/{username}")
    public void followingUser(@PathVariable String username, Principal principal) {
        this.userService.followingUser(principal.getName(), username);
    }

    @ApiOperation(value = "")
    @PreAuthorize("isAuthenticated() and authentication.principal.username != #username")
    @DeleteMapping("/me/following/users/{username}")
    public void cancelFollowingUser(@PathVariable String username, Principal principal) {
        this.userService.unFollowingUser(principal.getName(), username);
    }
}
