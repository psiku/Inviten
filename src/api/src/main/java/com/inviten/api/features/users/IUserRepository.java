package com.inviten.api.features.users;

import com.inviten.api.features.meetings.Meeting;

import java.util.List;

public interface IUserRepository {


    public User show(String id);
    public void create(User user);

    public void addMeeting(String userId, String meetingId);
}
