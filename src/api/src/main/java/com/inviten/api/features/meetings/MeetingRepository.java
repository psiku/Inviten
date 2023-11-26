package com.inviten.api.features.meetings;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class MeetingRepository implements IMeetingRepository {
    private final DynamoDbTable<Meeting> table;

    public MeetingRepository(DynamoDbEnhancedClient client) {
        table = client.table("meetings", TableSchema.fromBean(Meeting.class));
    }

    @Override
    public Meeting one(String id) {
        var key = Key.builder()
                .partitionValue(id)
                .build();

        return table.getItem(key);
    }

    @Override
    public void create(Meeting meeting) {
        table.putItem(meeting);
    }
    @Override
    public Meeting createAndSave(Meeting meeting){
        create(meeting);
        return meeting;
    }
}
