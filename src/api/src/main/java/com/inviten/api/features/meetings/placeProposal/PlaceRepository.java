package com.inviten.api.features.meetings.placeProposal;

import com.inviten.api.features.meetings.IMeetingRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import com.inviten.api.features.meetings.Meeting;

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

}
