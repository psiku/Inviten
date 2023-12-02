package com.inviten.api.features.meetings.date_proposal;
import org.springframework.stereotype.Repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import java.util.List;
import java.util.Optional;
@Repository
public class DateProposalRepository implements IDateProposalRepository {
    private final DynamoDbTable<DateProposal> dateProposalTable;

    public DateProposalRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dateProposalTable = dynamoDbEnhancedClient.table("DateProposals", TableSchema.fromBean(DateProposal.class));
    }

    @Override
    public DateProposal findOne(String meetingId, String proposalId) {
        try {
            Key key = Key.builder()
                    .partitionValue(meetingId)
                    .sortValue(proposalId)
                    .build();
            return dateProposalTable.getItem(key);
        } catch (DynamoDbException e) {

        }
        return null;
    }

    @Override
    public List<DateProposal> findByMeetingId(String meetingId) {
        return null;
    }

    @Override
    public void addDateProposal(String meetingId, DateProposal dateProposal) {
        try {
            dateProposalTable.putItem(dateProposal);
        } catch (DynamoDbException e) {

        }
    }

    @Override
    public void removeDateProposal(String meetingId, String proposalId) {
        try {
            Key key = Key.builder()
                    .partitionValue(meetingId)
                    .sortValue(proposalId)
                    .build();
            dateProposalTable.deleteItem(key);
        } catch (DynamoDbException e) {

        }
    }

    @Override
    public void voteForDateProposal(String meetingId, String proposalId, String voterId) {

    }

    @Override
    public void unvoteForDateProposal(String meetingId, String proposalId, String voterId) {

    }
}
