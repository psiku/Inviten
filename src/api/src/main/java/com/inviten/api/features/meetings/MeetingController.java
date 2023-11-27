<<<<<<< HEAD
package com.inviten.api.features.meetings;

import org.springframework.web.bind.annotation.*;

@RestController
public class MeetingController {
    private final IMeetingRepository meetingRepository;

    public MeetingController(IMeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @GetMapping("/meetings/{id}")
    Meeting one(@PathVariable String id) {
        return meetingRepository.one(id);
    }

    @PostMapping("/meetings")
    Meeting createAndSave(@RequestBody Meeting meeting){
        return meetingRepository.createAndSave(meeting) ;
    }
}
=======
package com.inviten.api.features.meetings;

import org.springframework.web.bind.annotation.*;


@RestController
public class MeetingController {
    private final IMeetingRepository meetingRepository;

    public MeetingController(IMeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @GetMapping("/meetings/{id}")
    Meeting one(@PathVariable String id) {
        return meetingRepository.one(id);
    }

    @PostMapping("/meetings")
    Meeting createAndSave(@RequestBody Meeting meeting) {
        return meetingRepository.createAndSave(meeting);
    }

    @PutMapping("meetings/{id}/users")
    public void addMember(@PathVariable String id, @RequestBody Member member){
        meetingRepository.addMember(id, member);
    }

    @DeleteMapping("meetings/{id}/users/{phoneNumber}")
    public void deleteMember(@PathVariable String id, @PathVariable String phoneNumber){
        meetingRepository.deleteMember(id, phoneNumber);
    }
}
>>>>>>> c1f624b (implementation of addMember and deleteMember functions)
