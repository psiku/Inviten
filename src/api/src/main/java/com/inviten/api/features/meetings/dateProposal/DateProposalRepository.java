package com.inviten.api.features.meetings.dateProposal;

import org.springframework.stereotype.Repository;
import com.inviten.api.features.meetings.Meeting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DateProposalRepository implements IDateProposalRepository {
    private final DynamoDbTable<Meeting> meetingTable;

    public DateProposalRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.meetingTable = dynamoDbEnhancedClient.table("meetings", TableSchema.fromBean(Meeting.class));
    }

    @Override
    public DateProposal findOne(String meetingId, String proposalId) {
        try {
            Key key = Key.builder()
                    .partitionValue(meetingId)
                    .build();
            Meeting meeting = meetingTable.getItem(key);
            if (meeting != null && meeting.getDateProposals() != null) {
                for (DateProposal proposal : meeting.getDateProposals()) {
                    if (proposal.getId().equals(proposalId)) {
                        return proposal;
                    }
                }
            }
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DateProposal> findByMeetingId(String meetingId) {
        try {
            Key key = Key.builder()
                    .partitionValue(meetingId)
                    .build();
            Meeting meeting = meetingTable.getItem(key);
            return meeting != null ? meeting.getDateProposals() : null;
        } catch (DynamoDbException e) {

            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void addDateProposal(String meetingId, DateProposal dateProposal) {
        try {
            Key key = Key.builder()
                    .partitionValue(meetingId)
                    .build();
            Meeting meeting = meetingTable.getItem(key);
            if (meeting != null) {
                List<DateProposal> proposals = meeting.getDateProposals();
                if (proposals == null) {
                    proposals = new ArrayList<>();
                }
                proposals.add(dateProposal);
                meeting.setDateProposals(proposals);
                meetingTable.updateItem(meeting);
            }
        } catch (DynamoDbException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void removeDateProposal(String meetingId, String proposalId) {
        try {
            Key key = Key.builder()
                    .partitionValue(meetingId)
                    .build();
            Meeting meeting = meetingTable.getItem(key);
            if (meeting != null && meeting.getDateProposals() != null) {
                meeting.getDateProposals().removeIf(proposal -> proposal.getId().equals(proposalId));
                meetingTable.updateItem(meeting);
            }
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void voteForDateProposal(String meetingId, String proposalId, String voterId) {

    }

    @Override
    public void unvoteForDateProposal(String meetingId, String proposalId, String voterId) {

    }
}
