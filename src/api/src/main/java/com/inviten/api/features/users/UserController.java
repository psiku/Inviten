package com.inviten.api.features.users;

import com.inviten.api.features.meetings.Meeting;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;

import java.util.List;

@RestController
public class UserController {

    private final IUserRepository userRepository;

    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("users/{phoneNumber}/meetings")
    public List<Meeting> getMeetingsForUser(@PathVariable String phoneNumber) {
        return userRepository.getMeetings(phoneNumber);
    }
}
