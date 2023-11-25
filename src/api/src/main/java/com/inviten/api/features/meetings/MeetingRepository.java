package com.inviten.api.features.meetings;

public class MeetingRepository implements IMeetingRepository{
    public Meeting one(String id) {
        var meeting = new Meeting();
        meeting.id = id;
        return meeting;
    }
}
