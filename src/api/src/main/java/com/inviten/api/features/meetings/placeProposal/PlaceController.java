package com.inviten.api.features.meetings.placeProposal;

import org.springframework.web.bind.annotation.*;

@RestController
public class PlaceController {
    private final IPlaceRepository placeRepository;

    public PlaceController(IPlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @PutMapping("/meetings/{meetingId}/places")
    void addPlaceProposal(@RequestBody Place place, @PathVariable String meetingId){
        placeRepository.addPlaceProposal(place, meetingId);
    }

    @DeleteMapping("/meetings/{meetingId}/places")
    void removePlaceProposal(@RequestBody Place place, @PathVariable String meetingId){
        placeRepository.removePlaceProposal(place, meetingId);
    }

    @PostMapping("/meetings/{meetingId}/places/{placeId}/vote/{phoneNumber}")
    void addVote(@PathVariable String meetingId, @PathVariable String placeId, @PathVariable String phoneNumber){
        placeRepository.addVote(meetingId, placeId, phoneNumber);
    }

    @PostMapping("/meetings/{meetingId}/places/{placeId}/unvote/{phoneNumber}")
    void removeVote(@PathVariable String meetingId, @PathVariable String placeId, @PathVariable String phoneNumber){
        placeRepository.removeVote(meetingId, placeId, phoneNumber);
    }

    @PostMapping("meetings/{meetingId}/places/{proposalId}/pick")
    void confirmPlace(@PathVariable String meetingId, @PathVariable String proposalId){
        placeRepository.confirmPlace(meetingId, proposalId);
    }
}
