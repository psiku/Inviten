package com.inviten.api.features.meetings.placeProposal;

public interface IPlaceRepository {
    public void addPlaceProposal(Place place, String meetingId);

    public void removePlaceProposal(Place place, String meetingId);

    public void addVote(String meetingId, String placeId);

    public void removeVote(String meetingId, String placeId);

    public void confirmPlace(String meetingId, String proposalId);
}
