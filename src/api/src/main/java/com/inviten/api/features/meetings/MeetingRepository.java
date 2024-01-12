<<<<<<< HEAD
<<<<<<< HEAD
package com.inviten.api.features.meetings;

=======
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
>>>>>>> 0508e92 (change invite methid)
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
<<<<<<< HEAD

public class MeetingRepository implements IMeetingRepository {
    private final DynamoDbTable<Meeting> table;

    public MeetingRepository(DynamoDbEnhancedClient client) {
        table = client.table("meetings", TableSchema.fromBean(Meeting.class));
=======
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
>>>>>>> 0508e92 (change invite methid)
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
<<<<<<< HEAD
=======

>>>>>>> 0508e92 (change invite methid)
        table.putItem(meeting);
    }
    @Override
    public Meeting createAndSave(Meeting meeting){
<<<<<<< HEAD
        create(meeting);
        return meeting;
    }
}
=======
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

<<<<<<< HEAD
<<<<<<< HEAD
    @Override
    public void addMember(String meetingId, Member member){
=======
>>>>>>> 6ff8976 (add invite method to add friend to meeting)

    @Override
    public void invite(String meetingId, String phoneNumber){
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

            List<String> userMeetings = user.getMeetingsIds() != null ? new ArrayList<>(user.getMeetingsIds()) : new ArrayList<>();
            if (!userMeetings.contains(meetingId)) {
                userMeetings.add(meetingId);
                user.setMeetingsIds(userMeetings);
                usersTable.putItem(user);
            }

            List<Member> participants = meeting.getParticipants() != null ? new ArrayList<>(meeting.getParticipants()) : new ArrayList<>();
            Member member = new Member();
            member.setPhoneNumber(hashedPhoneNumber);
            member.setRole(Role.getGuestRole());
            member.setNick(nameGenerator.getRandomWord());
            if (!participants.contains(member)) {
                participants.add(member);
                meeting.setParticipants(participants);
                table.putItem(meeting);
            }
        } catch (Exception e) {

        }
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
<<<<<<< HEAD
>>>>>>> 6d750cd (add leaveMeeting function)
=======


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
>>>>>>> 3d3ba5d (Added promote function)
}
<<<<<<< HEAD

>>>>>>> c1f624b (implementation of addMember and deleteMember functions)
=======
>>>>>>> 3f415bf (init)
=======
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
    public void invite(String meetingId, String phoneNumber){
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



        } catch (Exception e) {

        }
    }

    ///





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
}
>>>>>>> 0508e92 (change invite methid)
