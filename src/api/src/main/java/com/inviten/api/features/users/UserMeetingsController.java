package com.inviten.api.features.users;

import com.inviten.api.features.meetings.Meeting;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserMeetingsController {

    private final IUserMeetingsRepository userRepository;

    public UserMeetingsController(IUserMeetingsRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("users/{phoneNumber}")
    public UserMeetings show(@PathVariable String phoneNumber) {
        return userRepository.show(phoneNumber);
    }

    @PostMapping("users")
    void create(@RequestBody UserMeetings user) {
        userRepository.create(user);
    }

    @GetMapping("users/{phoneNumber}/meetings")
    public List<Meeting> getUsersMeetings(@PathVariable String phoneNumber) {
        return userRepository.getUsersMeetings(phoneNumber);
    }

    @DeleteMapping("users/{phoneNumber}")
    public void delete(@PathVariable String phoneNumber) {
        userRepository.delete(phoneNumber);
    }

}
