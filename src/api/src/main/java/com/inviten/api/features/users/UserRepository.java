package com.inviten.api.features.users;

import com.inviten.api.features.meetings.Meeting;
import com.inviten.api.features.meetings.Member;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserRepository implements IUserRepository {
    private final DynamoDbTable<Meeting> meetings;

    public UserRepository(DynamoDbEnhancedClient client) {
        meetings = client.table("meetings", TableSchema.fromBean(Meeting.class));
    }

    @Override
    public List<Meeting> getMeetings(String phoneNumber) {
        List<Meeting> userMeeteings = new ArrayList<>();
        for (Meeting meeting : meetings.scan().items()) {
            for (Member member : meeting.getParticipants()) {
                if (Objects.equals(member.getPhoneNumber(), phoneNumber)) {
                    userMeeteings.add(meeting);
                }
            }
        }
        if (userMeeteings.isEmpty())
        {
            return null;
        }
        else{
            return userMeeteings;
        }
    }
}
