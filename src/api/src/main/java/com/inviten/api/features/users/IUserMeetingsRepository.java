package com.inviten.api.features.users;

import com.inviten.api.features.meetings.Meeting;

import java.util.List;

public interface IUserMeetingsRepository {


    public UserMeetings show(String id);
    public void create(UserMeetings user);

    public void addMeeting(String userId, String meetingId);

    public void delete(String UserPhoneNumber);

    public List<Meeting> getUsersMeetings(String userPhoneNumber);
}
