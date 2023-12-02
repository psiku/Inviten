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
}
