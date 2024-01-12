package com.inviten.api.features.meetings.dateProposal;

import java.util.List;

public interface IDateProposalRepository {

    DateProposal findOne(String meetingId, String proposalId);

    List<DateProposal> findByMeetingId(String meetingId);

    DateProposal addDateProposal(String meetingId, DateProposal dateProposal);

    void removeDateProposal(String meetingId, String proposalId);

     DateProposal addVote(String meetingId, String proposalId);

    DateProposal removeVote(String meetingId, String proposalId);

    DateProposal confirmDate(String meetingId, String proposalId);
}
