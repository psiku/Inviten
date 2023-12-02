package com.inviten.api.features.meetings.placeProposal;

public interface IPlaceRepository {
    public void addPlaceProposal(Place place, String meetingId);

    public void removePlaceProposal(Place place, String meetingId);
}
