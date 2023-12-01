<<<<<<< HEAD
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
=======
package com.inviten.api.features.meetings;

import com.inviten.api.exception.ApiException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import com.inviten.api.exception.NotFoundException;
import com.inviten.api.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void addMember(String meetingId, Member member){

        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }

        List<Member> participants = meeting.getParticipants();
        if (participants == null) {
            participants = List.of(member);
        }
        else {
            participants.add(member);
        }
        meeting.setParticipants(participants);
        table.putItem(meeting);
    }


    @Override
    public void deleteMember(String meetingId, String phoneNumber){
        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }

        List<Member> participants = meeting.getParticipants();
        int indexOfMember = -1;
        for (int i = 0; i < participants.size(); i++) {
            Member member = participants.get(i);
            if (phoneNumber.equals(member.getPhoneNumber())) {
                indexOfMember = i;
                break;
            }
        }
        if (indexOfMember != -1){
            participants.remove(indexOfMember);
            meeting.setParticipants(participants);
            table.putItem(meeting);
        }
    }
}

>>>>>>> c1f624b (implementation of addMember and deleteMember functions)
