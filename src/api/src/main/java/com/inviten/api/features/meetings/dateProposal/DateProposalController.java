package com.inviten.api.features.meetings.dateProposal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meetings/{meetingId}/dates")
public class DateProposalController {

    private final IDateProposalRepository dateProposalRepository;

    @Autowired
    public DateProposalController(IDateProposalRepository dateProposalRepository) {
        this.dateProposalRepository = dateProposalRepository;
    }

    @PutMapping
    public DateProposal addDateProposal(@PathVariable String meetingId, @RequestBody DateProposal dateProposal) {
        return dateProposalRepository.addDateProposal(meetingId, dateProposal);
    }

    @GetMapping("/{proposalId}")
    public DateProposal getDateProposal(@PathVariable String meetingId, @PathVariable String proposalId) {
        return dateProposalRepository.findOne(meetingId, proposalId);
    }

    @DeleteMapping("/{proposalId}")
    public void removeDateProposal(@PathVariable String meetingId, @PathVariable String proposalId) {
        dateProposalRepository.removeDateProposal(meetingId, proposalId);
    }


    @PostMapping("/{proposalId}/vote")
    public DateProposal addVote(@PathVariable String meetingId, @PathVariable String proposalId) {
       return dateProposalRepository.addVote(meetingId, proposalId);
    }

    @PostMapping("/{proposalId}/unvote")
    public DateProposal removeVote(@PathVariable String meetingId, @PathVariable String proposalId) {
        return dateProposalRepository.removeVote(meetingId, proposalId);
    }

    @PostMapping("/{proposalId}/schedule")
    public DateProposal confirmDate(@PathVariable String meetingId, @PathVariable String proposalId) {
        return dateProposalRepository.confirmDate(meetingId, proposalId);
    }
}