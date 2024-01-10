package com.inviten.api.features.users;

import com.inviten.api.features.meetings.Meeting;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserMeetingsController {

    private final IUserMeetingsRepository userRepository;

    public UserMeetingsController(IUserMeetingsRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("users/{phoneNumber}")
    public User show(@PathVariable String phoneNumber) {
        return userRepository.show(phoneNumber);
    }

    @PostMapping("users")
    void create(@RequestBody User user) {
        userRepository.create(user);
    }

//    @GetMapping("users/{phoneNumber}/meetings")
//    public List<Meeting> getUsersMeetings(@PathVariable String phoneNumber) {
//        return userRepository.getUsersMeetings(phoneNumber);
//    }

    @GetMapping("users/meetings")
    public List<Meeting> getUsersMeetings() {
        // Pobranie aktualnego obiektu uwierzytelnienia
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Pobranie numeru telefonu z obiektu uwierzytelnienia
        // Zakładamy, że numer telefonu jest nazwą użytkownika (principal)
        String phoneNumber = (String) authentication.getPrincipal();
        System.out.println(phoneNumber);

        // Wywołanie metody repozytorium z numerem telefonu
        return userRepository.getUsersMeetings(phoneNumber);
    }

    @DeleteMapping("users/{phoneNumber}")
    public void delete(@PathVariable String phoneNumber) {
        userRepository.delete(phoneNumber);
    }


}
