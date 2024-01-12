package com.inviten.api.features.meetings.dateProposal;

import com.inviten.api.features.meetings.IMeetingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import com.inviten.api.features.meetings.Meeting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class DateProposalRepository implements IDateProposalRepository {
    private final DynamoDbTable<Meeting> meetingTable;

    private final IMeetingRepository meetingRepository;

    public DateProposalRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient, IMeetingRepository meetingRepository) {
        this.meetingTable = dynamoDbEnhancedClient.table("meetings", TableSchema.fromBean(Meeting.class));
        this.meetingRepository = meetingRepository;
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
    public DateProposal addDateProposal(String meetingId, DateProposal dateProposal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String phoneNumber = (String) authentication.getPrincipal();
        dateProposal.setProposedBy(phoneNumber);

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
        return dateProposal;
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

    private void sortDateProposals(Meeting meeting) {
        List<DateProposal> dateProposals = meeting.getDateProposals();
        dateProposals.sort((dp1, dp2) -> dp2.getVotes().size() - dp1.getVotes().size());
        meeting.setDateProposals(dateProposals);
        meetingTable.updateItem(meeting);
    }



    @Override
    public DateProposal addVote(String meetingId, String proposalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String phoneNumber = (String) authentication.getPrincipal();

        Meeting meeting = meetingRepository.one(meetingId);
        if(meeting.getIsDateChosen()){
            return null;
        }
        List<DateProposal> dateProposals = meeting.getDateProposals();
        if(dateProposals == null) {
            List<DateProposal> newDateProposals = new ArrayList<>();
            dateProposals = newDateProposals;
        }
        int index_of_proposal = -1;

        // znajduje to spotkanie w liscie spotkan i zapisuje jego index
        for(int i = 0; i < dateProposals.size(); i++){
            if(dateProposals.get(i).getId().equals(proposalId)){
                index_of_proposal = i;
                break;
            }
        }
        DateProposal currentProposal = dateProposals.get(index_of_proposal);
        List<String> votes = currentProposal.getVotes();
        if(votes == null)
        {
            votes = List.of(phoneNumber);
        }
        else{
            votes.add(phoneNumber);
        }

//        Iterator<DateProposal> iterator = dateProposals.iterator();
//        while (iterator.hasNext()) {
//            DateProposal currentPlace = iterator.next();
//            if (currentPlace.getId().equals(proposalId)) {
//                List<String> votes = currentPlace.getVotes();
//                if(votes == null)
//                {
//                    votes = List.of(phoneNumber);
//                }
//                else{
//                    votes.add(phoneNumber);
//                }
//                currentPlace.setVotes(votes);
//                break;
//            }
//        }
        sortDateProposals(meeting);
        meeting.setDateProposals(dateProposals);
        meetingTable.updateItem(meeting);
        return currentProposal;
    }

    @Override
    public void removeVote(String meetingId, String proposalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String phoneNumber = (String) authentication.getPrincipal();

        Meeting meeting = meetingRepository.one(meetingId);
        if(meeting.getIsDateChosen()){
            return;
        }
        List<DateProposal> dateProposals = meeting.getDateProposals();
        if(dateProposals == null) {
            List<DateProposal> newDateProposals = new ArrayList<>();
            dateProposals = newDateProposals;
        }
        Iterator<DateProposal> iterator = dateProposals.iterator();
        while (iterator.hasNext()) {
            DateProposal currentPlace = iterator.next();
            if (currentPlace.getId().equals(proposalId)) {
                List<String> votes = currentPlace.getVotes();
                if(votes == null)
                {
                    votes = List.of(phoneNumber);
                }
                else{
                    votes.remove(phoneNumber);
                }
                currentPlace.setVotes(votes);
                break;
            }
        }
        sortDateProposals(meeting);
        meeting.setDateProposals(dateProposals);
        meetingTable.updateItem(meeting);
    }

    @Override
    public DateProposal confirmDate(String meetingId, String proposalId){
        Meeting meeting = meetingRepository.one(meetingId);
        List<DateProposal> dateProposals = meeting.getDateProposals();


        // znajdź proposal o tym id
        int index_of_proposal = -1;
        for(int i = 0; i < dateProposals.size(); i++){
            if(dateProposals.get(i).getId().equals(proposalId)){
                index_of_proposal = i;
                break;
            }
        }
        DateProposal currentProposal = dateProposals.get(index_of_proposal);
        meeting.setDate(currentProposal.getProposedDate());
        meeting.setIsDateChosen(true);
        meetingTable.updateItem(meeting);
        return currentProposal;
    }
}
