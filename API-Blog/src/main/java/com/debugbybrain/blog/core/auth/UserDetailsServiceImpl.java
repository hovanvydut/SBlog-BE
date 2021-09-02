package com.debugbybrain.blog.core.auth;

import com.debugbybrain.blog.core.user.UserRepository;
import com.debugbybrain.blog.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.getUserAndRoleByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new MyUserDetails(user);
    }

}
