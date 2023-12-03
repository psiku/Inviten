package com.inviten.api.features.meetings.dateProposal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meetings/{meetingId}/date")
public class DateProposalController {

    private final IDateProposalRepository dateProposalRepository;

    @Autowired
    public DateProposalController(IDateProposalRepository dateProposalRepository) {
        this.dateProposalRepository = dateProposalRepository;
    }

    @PutMapping
    public void addDateProposal(@PathVariable String meetingId, @RequestBody DateProposal dateProposal) {
        dateProposalRepository.addDateProposal(meetingId, dateProposal);
    }

    @GetMapping("/{proposalId}")
    public DateProposal getDateProposal(@PathVariable String meetingId, @PathVariable String proposalId) {
        return dateProposalRepository.findOne(meetingId, proposalId);
    }

    @DeleteMapping("/{proposalId}")
    public void removeDateProposal(@PathVariable String meetingId, @PathVariable String proposalId) {
        dateProposalRepository.removeDateProposal(meetingId, proposalId);
    }



    @PutMapping("/{proposalId}/user")
    void addVote(@PathVariable String meetingId, @PathVariable String proposalId, @RequestBody String phoneNumber){
        dateProposalRepository.addVote(meetingId, proposalId, phoneNumber);
    }

    @DeleteMapping("/{proposalId}/user/{phoneNumber}")
    void removeVote(@PathVariable String meetingId, @PathVariable String proposalId, @PathVariable String phoneNumber){
        dateProposalRepository.removeVote(meetingId, proposalId, phoneNumber);
    }

//    @GetMapping
//    public List<DateProposal> getDateProposals(@PathVariable String meetingId) {
//        return dateProposalRepository.findByMeetingId(meetingId);
//    }
}
