package com.inviten.api.features.users;

import com.inviten.api.features.meetings.Meeting;
import com.inviten.api.features.meetings.Member;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserRepository implements IUserRepository {

    private final DynamoDbTable<User> users;
    private final DynamoDbTable<Meeting> meetings;

    public UserRepository(DynamoDbEnhancedClient client) {
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
    public void addMeeting(String userId, String meetingId){
        User user = show(userId);
        List<String> meetings = user.getMeetingsIds();
        if (meetings == null) {
            meetings = List.of(meetingId);
        }
        else {
            meetings.add(meetingId);
        }
        user.setMeetingsIds(meetings);
        users.putItem(user);
    }

//    @Override
//    public List<Meeting> getMeetings(String phoneNumber) {
//
//        List<Meeting> userMeetings = new ArrayList<>();
//
//        ScanEnhancedRequest request = ScanEnhancedRequest.builder()
//                .consistentRead(true)
//                .attributesToProject("id", "participants")
//                .build();
//
//        PageIterable<Meeting> pagedResults = meetings.scan(request);
//
//        pagedResults.stream().forEach(page -> {
//            List<Meeting> filteredMeetings = page.items().stream()
//                    .filter(meeting -> meeting != null && meeting.getParticipants() != null)
//                    .filter(meeting -> meeting.getParticipants().stream()
//                            .anyMatch(member -> member != null && Objects.equals(member.getPhoneNumber(), phoneNumber)))
//                    .collect(Collectors.toList());
//
//            userMeetings.addAll(filteredMeetings);
//        });

//        if (userMeetings.isEmpty())
//        {
//            return Collections.emptyList();
//        }
//        else{
//            return userMeetings;
//        }
    }
