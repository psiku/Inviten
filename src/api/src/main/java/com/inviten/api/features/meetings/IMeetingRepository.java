package com.inviten.api.features.meetings;

public interface IMeetingRepository {
    public Meeting one(String id);

    public void create(Meeting meeting);

    public Meeting createAndSave(Meeting meeting);
}
