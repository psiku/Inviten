package com.inviten.api.features.meetings.placeProposal;

import com.inviten.api.features.meetings.IMeetingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import com.inviten.api.features.meetings.Meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

@Service
public class PlaceRepository implements IPlaceRepository{

    private final IMeetingRepository meetingRepository;

    public PlaceRepository(IMeetingRepository meetingRepository, DynamoDbEnhancedClient client) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public void addPlaceProposal(Place place, String id) {
        Meeting meeting = meetingRepository.one(id);
        if(meeting.getIsPlaceChosen()){
            return;
        }
        List<Place> places = meeting.getPlaceProposals();
        if(places == null){
            places = List.of(place);
        }
        else {
            places.add(place);
        }
        meeting.setPlaceProposals(places);
        meetingRepository.put(meeting);
    }

    @Override
    public void removePlaceProposal (Place place, String id){
        Meeting meeting = meetingRepository.one(id);
        if(meeting.getIsPlaceChosen()){
            return;
        }
        List<Place> places = meeting.getPlaceProposals();
        if(places != null){
            Iterator<Place> iterator = places.iterator();
            while (iterator.hasNext()) {
                Place currentPlace = iterator.next();
                if (currentPlace.getId().equals(place.getId())) {
                    iterator.remove();
                    break;
                }
            }
        }
        meeting.setPlaceProposals(places);
        meetingRepository.put(meeting);
    }

    private void sortPlaces(Meeting meeting) {
        List<Place> places = meeting.getPlaceProposals();
        places.sort((dp1, dp2) -> dp2.getVotes().size() - dp1.getVotes().size());
        meeting.setPlaceProposals(places);
        meetingRepository.put(meeting);
    }

    @Override
    public void addVote(String meetingId, String placeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String phoneNumber = (String) authentication.getPrincipal();

        Meeting meeting = meetingRepository.one(meetingId);
        if(meeting.getIsPlaceChosen()){
            return;
        }
        List<Place> places = meeting.getPlaceProposals();
        if(places == null) {
            List<Place> newPlaces = new ArrayList<>();
            places = newPlaces;
        }
        Iterator<Place> iterator = places.iterator();
        while (iterator.hasNext()) {
            Place currentPlace = iterator.next();
            if (currentPlace.getId().equals(placeId)) {
                List<String> votes = currentPlace.getVotes();
                if(votes == null)
                {
                    votes = List.of(phoneNumber);
                }
                else{
                    votes.add(phoneNumber);
                }
                currentPlace.setVotes(votes);
                break;
            }
        }
        sortPlaces(meeting);
        meeting.setPlaceProposals(places);
        meetingRepository.put(meeting);
    }

    @Override
    public void removeVote(String meetingId, String placeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String phoneNumber = (String) authentication.getPrincipal();

        Meeting meeting = meetingRepository.one(meetingId);
        if(meeting.getIsPlaceChosen()){
            return;
        }
        List<Place> places = meeting.getPlaceProposals();
        if(places == null) {
            List<Place> newPlaces = new ArrayList<>();
            places = newPlaces;
        }
        Iterator<Place> iterator = places.iterator();
        while (iterator.hasNext()) {
            Place currentPlace = iterator.next();
            if (currentPlace.getId().equals(placeId)) {
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
        sortPlaces(meeting);
        meeting.setPlaceProposals(places);
        meetingRepository.put(meeting);
    }

    @Override
    public void confirmPlace(String meetingId, String proposalId){
        Meeting meeting = meetingRepository.one(meetingId);
        List<Place> placeProposals = meeting.getPlaceProposals();
        for(Place proposal: placeProposals){
            if(proposal.getId().equals(proposalId)){
                meeting.setPlace(proposal);
                break;
            }
        }
        meeting.setIsPlaceChosen(true);
        meetingRepository.put(meeting);
    }

}
