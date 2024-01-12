package com.inviten.api.features.meetings.placeProposal;

import org.springframework.web.bind.annotation.*;

@RestController
public class PlaceController {
    private final IPlaceRepository placeRepository;

    public PlaceController(IPlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @PutMapping("/meetings/{meetingId}/places")
    public Place addPlaceProposal(@RequestBody Place place, @PathVariable String meetingId){
        return placeRepository.addPlaceProposal(place, meetingId);
    }

    @DeleteMapping("/meetings/{meetingId}/places")
    void removePlaceProposal(@RequestBody Place place, @PathVariable String meetingId){
        placeRepository.removePlaceProposal(place, meetingId);
    }

    @PostMapping("/meetings/{meetingId}/places/{placeId}/vote")
    public Place addVote(@PathVariable String meetingId, @PathVariable String placeId){
        return placeRepository.addVote(meetingId, placeId);
    }

    @PostMapping("/meetings/{meetingId}/places/{placeId}/unvote")
    public Place removeVote(@PathVariable String meetingId, @PathVariable String placeId){
        return placeRepository.removeVote(meetingId, placeId);
    }

    @PostMapping("meetings/{meetingId}/places/{proposalId}/pick")
    public Place confirmPlace(@PathVariable String meetingId, @PathVariable String proposalId){
        return placeRepository.confirmPlace(meetingId, proposalId);
    }
}
