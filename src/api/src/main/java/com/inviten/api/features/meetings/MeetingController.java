package com.inviten.api.features.meetings;

import com.inviten.api.features.users.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
public class MeetingController {
    private final IMeetingRepository meetingRepository;

    public MeetingController(IMeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @GetMapping("meetings/{meetingId}")
    Meeting one(@PathVariable String meetingId) {
        return meetingRepository.one(meetingId);
    }

    @PostMapping("/meetings")
    Meeting createAndSave(@RequestBody Meeting meeting) {
        return meetingRepository.createAndSave(meeting);
    }

    @PutMapping("meetings/{meetingId}/users/{phoneNumber}")
    public Member invite(@PathVariable String meetingId, @PathVariable String phoneNumber){
        return meetingRepository.invite(meetingId, phoneNumber);
    }

    @DeleteMapping("meetings/{meetingId}/users/{phoneNumber}")
    public void deleteMember(@PathVariable String meetingId, @PathVariable String phoneNumber){

        meetingRepository.deleteMember(meetingId, phoneNumber);
    }

    @DeleteMapping("meetings/{meetingId}/users")
    public void leaveMeeting(@PathVariable String meetingId){
        meetingRepository.leaveMeeting(meetingId);
    }

    @PostMapping("/meetings/{meetingId}/users/{userId}/role/promote")
    public void promoteMember(@PathVariable String meetingId, @PathVariable String userId){
        meetingRepository.promoteMember(meetingId, userId);
    }

    @PostMapping("/meetings/{meetingId}/users/{userId}/role/degradation")
    public void degradateMember (@PathVariable  String meetingId, @PathVariable String userId){
        meetingRepository.degradateMember(meetingId, userId);
    }

    @PutMapping("meetings/{meetingId}/icon/{iconName}")
    public String addIcon(@PathVariable String meetingId, @PathVariable String iconName){
        return meetingRepository.addIcon(meetingId, iconName);
    }

    @DeleteMapping("meetings/{meetingId}/icon")
    public void deleteIcon(@PathVariable String meetingId){

        meetingRepository.deleteIcon(meetingId);
    }

    @PutMapping("meetings/{meetingId}/duration/{durationMinutes}")
    public Integer addDuration(@PathVariable String meetingId, @PathVariable Integer durationMinutes){
        return meetingRepository.addDuration(meetingId, durationMinutes);
    }

    @DeleteMapping("meetings/{meetingId}/duration")
    public void deleteDuration(@PathVariable String meetingId){

        meetingRepository.deleteDuration(meetingId);
    }
}
