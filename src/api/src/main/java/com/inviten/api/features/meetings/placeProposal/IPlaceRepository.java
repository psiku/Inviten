package com.inviten.api.features.meetings.placeProposal;

public interface IPlaceRepository {
    public Place addPlaceProposal(Place place, String meetingId);

    public void removePlaceProposal(Place place, String meetingId);

    public Place addVote(String meetingId, String placeId);

    public void removeVote(String meetingId, String placeId);

    public Place confirmPlace(String meetingId, String proposalId);
}
