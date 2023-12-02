package com.inviten.api.features.meetings.date_proposal;

import java.util.List;

public interface IDateProposalRepository {

    DateProposal findOne(String meetingId, String proposalId);

    List<DateProposal> findByMeetingId(String meetingId);

    void addDateProposal(String meetingId, DateProposal dateProposal);

    void removeDateProposal(String meetingId, String proposalId);

    void voteForDateProposal(String meetingId, String proposalId, String voterId);

    void unvoteForDateProposal(String meetingId, String proposalId, String voterId);
}
