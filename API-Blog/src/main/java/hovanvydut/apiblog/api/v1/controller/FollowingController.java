package hovanvydut.apiblog.api.v1.controller;

import hovanvydut.apiblog.core.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("isAuthenticated() and authentication.principal.username != #username")
    @PutMapping("/me/following/users/{username}")
    public void followingUser(@PathVariable String username, Principal principal) {
        this.userService.followingUser(principal.getName(), username);
    }

    @PreAuthorize("isAuthenticated() and authentication.principal.username != #username")
    @DeleteMapping("/me/following/users/{username}")
    public void cancelFollowingUser(@PathVariable String username, Principal principal) {
        this.userService.unFollowingUser(principal.getName(), username);
    }
}
