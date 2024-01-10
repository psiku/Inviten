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

import com.inviten.api.features.users.User;
import com.inviten.api.features.users.UserMeetingsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import com.inviten.api.exception.NotFoundException;

import java.util.List;


public class MeetingRepository implements IMeetingRepository {
    private final DynamoDbTable<Meeting> table;
    private final DynamoDbTable<User> usersTable;

    private final UserMeetingsRepository userRepository;


    public MeetingRepository(DynamoDbEnhancedClient client) {
        table = client.table("meetings", TableSchema.fromBean(Meeting.class));
        usersTable = client.table("users", TableSchema.fromBean(User.class));
        this.userRepository = new UserMeetingsRepository(client);
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

<<<<<<< HEAD
    @Override
    public void addMember(String meetingId, Member member){

        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }

        String UserPhoneNumber = member.getPhoneNumber();

        User user = userRepository.show(UserPhoneNumber);
        if (user == null) {
            user = new User();
            user.setPhoneNumber(UserPhoneNumber);
            userRepository.create(user);
        }

        // lista spotkań MeeeetingUsera
        List<String> userMeetings = user.getMeetingsIds();
        if(userMeetings == null){
            userMeetings = List.of(meetingId);
        }
        else{
            userMeetings.add(meetingId);
        }

        List<Member> participants = meeting.getParticipants();
        if (participants == null) {
            participants = List.of(member);
        }
        else {
            participants.add(member);
        }
        // ustawiamy listę spotkań
        user.setMeetingsIds(userMeetings);
        meeting.setParticipants(participants);
        usersTable.putItem(user);
        table.putItem(meeting);
    }


    @Override
    public void deleteMember(String meetingId, String phoneNumber){
        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }

        User user = userRepository.show(phoneNumber);

        List<String> userMeetings = user.getMeetingsIds();
        int indexOfUserMeeting = -1;
        for(int j = 0; j < userMeetings.size(); j++){
            String userMeetingId = userMeetings.get(j);
            if(userMeetingId.equals(meetingId)){
                indexOfUserMeeting = j;
                break;
            }
        }

        if(indexOfUserMeeting != -1){
            userMeetings.remove(indexOfUserMeeting);
            user.setMeetingsIds(userMeetings);
            usersTable.putItem(user);
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
<<<<<<< HEAD
=======
    public void put(Meeting meeting)
    {
        table.putItem(meeting);
    }

>>>>>>> 20c175c (Implement placeProposal)
=======
    @Override
    public void put(Meeting meeting) {
        table.putItem(meeting);
    }
<<<<<<< HEAD
>>>>>>> 16719cb (added put method)
=======

    @Override
    public void leaveMeeting(@PathVariable String meetingId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String phoneNumber = (String) authentication.getPrincipal();

        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }

        User user = userRepository.show(phoneNumber);

        List<String> userMeetings = user.getMeetingsIds();
        int indexOfUserMeeting = -1;
        for(int j = 0; j < userMeetings.size(); j++){
            String userMeetingId = userMeetings.get(j);
            if(userMeetingId.equals(meetingId)){
                indexOfUserMeeting = j;
                break;
            }
        }

        if(indexOfUserMeeting != -1){
            userMeetings.remove(indexOfUserMeeting);
            user.setMeetingsIds(userMeetings);
            usersTable.putItem(user);
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
>>>>>>> 6d750cd (add leaveMeeting function)
}
<<<<<<< HEAD

>>>>>>> c1f624b (implementation of addMember and deleteMember functions)
=======
>>>>>>> 3f415bf (init)
