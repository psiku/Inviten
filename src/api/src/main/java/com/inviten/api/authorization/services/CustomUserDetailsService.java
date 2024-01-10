package com.inviten.api.authorization.services;

import com.inviten.api.features.users.User;
import com.inviten.api.features.users.UserMeetingsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMeetingsRepository userRepository;

    public CustomUserDetailsService(UserMeetingsRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findUserByID(id);
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getPhoneNumber())
                        .password(user.getPhoneNumber())
                        .roles(roles.toArray(new String[0]))
                        .build();
        return userDetails;
    }
}
