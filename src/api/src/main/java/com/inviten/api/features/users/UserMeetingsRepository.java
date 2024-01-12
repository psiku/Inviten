package com.inviten.api.features.users;

import com.inviten.api.features.meetings.Meeting;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserMeetingsRepository implements IUserMeetingsRepository {

    private final DynamoDbTable<User> users;
    private final DynamoDbTable<Meeting> meetings;

    public UserMeetingsRepository(DynamoDbEnhancedClient client) {
        meetings = client.table("meetings", TableSchema.fromBean(Meeting.class));
        users = client.table("users", TableSchema.fromBean(User.class));
    }

    public User show(String id) {
        var key = Key.builder()
                .partitionValue(id)
                .build();

        return users.getItem(key);
    }

    @Override
    public void create(User user) {
        users.putItem(user);
    }

    @Override
    public void addMeeting(String userId, String meetingId) {
        User user = show(userId);
        List<String> meetings = user.getMeetingsIds();
        if (meetings == null) {
            meetings = List.of(meetingId);
        } else {
            meetings.add(meetingId);
        }
        user.setMeetingsIds(meetings);
        users.putItem(user);
    }

    @Override
    public void delete(String UserPhoneNumber) {
        users.deleteItem(Key.builder()
                .partitionValue(UserPhoneNumber)
                .build());
    }

    @Override
    public List<Meeting> getUsersMeetings(String userPhoneNumber) {
        //chcemy jego listę więc zakładamy że istnieje
        User user = show(userPhoneNumber);

        //pobieramy jego listę id spotkań
        List<String> userMeetingsIds = user.getMeetingsIds();

        //to chcemy zwracać
        List<Meeting> userMeetings = new ArrayList<>();
        if (userMeetingsIds != null && !userMeetingsIds.isEmpty()) {

            for (String meetingId : userMeetingsIds) {
                Expression filterExpression = Expression.builder()
                        .expression("#id = :val")
                        .expressionNames(Collections.singletonMap("#id", "id"))
                        .expressionValues(Collections.singletonMap(":val", AttributeValue.builder().s(meetingId).build()))
                        .build();

                ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
                        .consistentRead(true)
                        .filterExpression(filterExpression)
                        .build();

                PageIterable<Meeting> pagedResults = meetings.scan(scanEnhancedRequest);

                pagedResults.items().forEach(meeting -> {
                    userMeetings.add(meeting);
                });
            }
        }
        return userMeetings;
    }

    @Override
    public User findUserByID(String id){
    // sprawdź czy istnieje w bazie
        User user = show(id);

    // jak nie to stwórz i zwróć
        if (user == null) {
            user = new User();
            user.setPhoneNumber(id);
//            create(user);
        }
    // jak tak to zwróć
        return user;
    }
}




