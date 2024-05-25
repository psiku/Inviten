package com.inviten.api.features.meetings;

import com.inviten.api.authorization.converter.TimeConverter;
import com.inviten.api.authorization.hashing.PhoneHash;
import com.inviten.api.features.users.User;
import com.inviten.api.features.users.UserMeetingsRepository;
import com.inviten.api.generator.NameGenerator;
import org.apache.catalina.filters.RateLimitFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import com.inviten.api.exception.NotFoundException;

import javax.naming.Name;
import java.util.ArrayList;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String phoneNumber = (String) authentication.getPrincipal();

        // stworzenie mebera ownera
        Member member = new Member();
        member.setPhoneNumber(phoneNumber);
        Role role = new Role();
        member.setRole(role.getOwnerRole());
        NameGenerator nameGenerator = new NameGenerator();
        member.setNick(nameGenerator.getRandomWord());

        // ustawienie nowych participantów
        List<Member> participants = meeting.getParticipants();
        if(participants == null){
            participants = List.of(member);
        }else {
            participants.add(member);
        }
        meeting.setParticipants(participants);

        // dodanie daty utworzenia
        long timestamp = System.currentTimeMillis();
        String createdAt = new TimeConverter().convertTimestampToIso8601(timestamp);
        meeting.setCreatedAt(createdAt);

        // aktualizowanie usera
        User user = userRepository.show(phoneNumber);
        List<String> meetingsIds = user.getMeetingsIds();
        if (meetingsIds == null) {
            meetingsIds = List.of(meeting.getId());
        }else {
            meetingsIds.add(meeting.getId());
        }
        user.setMeetingsIds(meetingsIds);

        // dodanie do tabeli users
        usersTable.putItem(user);

        // dodanie do tabeli meetings
        create(meeting);
        return meeting;
    }


    @Override
    public Member invite(String meetingId, String phoneNumber){
        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }

        PhoneHash phoneHash = new PhoneHash();
        NameGenerator nameGenerator = new NameGenerator();

        try {
            String hashedPhoneNumber = phoneHash.hashPhoneNumber(phoneNumber);


            User user = userRepository.show(hashedPhoneNumber);
            if (user == null) {
                user = new User();
                user.setPhoneNumber(hashedPhoneNumber);
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


            Member member = new Member();
            member.setPhoneNumber(hashedPhoneNumber);
            member.setRole(Role.getGuestRole());
            member.setNick(nameGenerator.getRandomWord());

            List<Member> participants = meeting.getParticipants();
            if (participants == null) {
                participants = List.of(member);
            }else {
                participants.add(member);
            }
            user.setMeetingsIds(userMeetings);
            meeting.setParticipants(participants);
            usersTable.putItem(user);
            table.putItem(meeting); //kom  1
            return member;
        } catch (Exception e) {
        }
        return null;
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
    @Override
    public void put(Meeting meeting) {
        table.putItem(meeting);
    }

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


    @Override
    public void promoteMember(String meetingId, String userId){

        //pobieranie tokenu użytkownika który próbuje użyć tej metody w celu pobrania jego ID (numeru zahaszowanego)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = (String) authentication.getPrincipal();



        //w danym spotkaniu
        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }

        String memberRole = "";

        //sprawdzenie rangi użytkownika który próbuje użyć tej metody
        List<Member> participants = meeting.getParticipants();
        for (int i = 0; i < participants.size(); i++) {
            Member member = participants.get(i);
            if (phoneNumber.equals(member.getPhoneNumber())) {
                memberRole = member.getRole();
                break;
            }
        }

        if(memberRole.equals("owner") || memberRole.equals("admin")){

            // szukamy użytkownika o danym id, którego będziemy chcieli awansować
            int indexOfMember = -1;
            for (int i = 0; i < participants.size(); i++) {
                Member member = participants.get(i);
                if (userId.equals(member.getPhoneNumber())) {
                    indexOfMember = i;
                    break;
                }
            }
            // nadajemu mu nową rangę i uzupełniamy tablę
            Member memberToPromote = participants.get(indexOfMember);
            memberToPromote.setRole("admin");
            participants.set(indexOfMember, memberToPromote);
            meeting.setParticipants(participants);
            table.putItem(meeting);

        }
    }

    @Override
    public void degradateMember (String meetingId, String userId){
        //pobieranie tokenu użytkownika który próbuje użyć tej metody w celu pobrania jego ID (numeru zahaszowanego)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = (String) authentication.getPrincipal();



        //w danym spotkaniu
        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }

        String memberRole = "";

        //sprawdzenie rangi użytkownika który próbuje użyć tej metody
        List<Member> participants = meeting.getParticipants();
        for (int i = 0; i < participants.size(); i++) {
            Member member = participants.get(i);
            if (phoneNumber.equals(member.getPhoneNumber())) {
                memberRole = member.getRole();
                break;
            }
        }

        if(memberRole.equals("owner") || memberRole.equals("admin")){

            // szukamy użytkownika o danym id, którego będziemy chcieli awansować
            int indexOfMember = -1;
            for (int i = 0; i < participants.size(); i++) {
                Member member = participants.get(i);
                if (userId.equals(member.getPhoneNumber())) {
                    indexOfMember = i;
                    break;
                }
            }
            // nadajemu mu nową rangę i uzupełniamy tablę
            Member memberToDegrade = participants.get(indexOfMember);
            memberToDegrade.setRole("guest");
            participants.set(indexOfMember, memberToDegrade);
            meeting.setParticipants(participants);
            table.putItem(meeting);

        }
    }

    @Override
    public String addIcon(String meetingId, String iconName){
        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }
        meeting.setIcon(iconName);
        table.putItem(meeting);
        return null;
    }

    @Override
    public void deleteIcon(String meetingId){
        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }
        meeting.setIcon(null);
        table.putItem(meeting);
    }

    @Override
    public Integer addDuration(String meetingId, Integer durationMinutes){
        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }
        meeting.setDuration(durationMinutes);
        table.putItem(meeting);
        return null;
    }

    @Override
    public void deleteDuration(String meetingId){
        Meeting meeting = one(meetingId);
        if (meeting == null) {
            throw new NotFoundException();
        }
        meeting.setDuration(null);
        table.putItem(meeting);
    }

}
