package com.inviten.api.features.meetings.placeProposal;

import org.springframework.web.bind.annotation.*;

@RestController
public class PlaceController {
    private final IPlaceRepository placeRepository;

    public PlaceController(IPlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @PutMapping("/meetings/{id}/places")
    void addPlaceProposal(@RequestBody Place place, @PathVariable String id){
        placeRepository.addPlaceProposal(place, id);
    }

    @DeleteMapping("/meetings/{id}/places")
    void removePlaceProposal(@RequestBody Place place, @PathVariable String id){
        placeRepository.removePlaceProposal(place, id);
    }

    @PutMapping("/meetings/{meetingId}/places/{placeId}/user")
    void addVote(@PathVariable String meetingId, @PathVariable String placeId, @RequestBody String phoneNumber){
        placeRepository.addVote(meetingId, placeId, phoneNumber);
    }

    @DeleteMapping("/meetings/{meetingId}/places/{placeId}/user/{phoneNumber}")
    void removeVote(@PathVariable String meetingId, @PathVariable String placeId, @PathVariable String phoneNumber){
        placeRepository.removeVote(meetingId, placeId, phoneNumber);
    }

    @PutMapping("meetings/{meetingId}/place/{proposalId}/confirm")
    void confirmPlace(@PathVariable String meetingId, @PathVariable String proposalId){
        placeRepository.confirmPlace(meetingId, proposalId);
    }
}
