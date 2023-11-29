package com.inviten.api.features.users;

import com.inviten.api.features.meetings.Meeting;

import java.util.List;

public interface IUserRepository {

    public List<Meeting>getMeetings(String phoneNumber);
}
