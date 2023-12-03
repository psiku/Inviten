package com.inviten.api.features.meetings.date_proposal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meetings/{meetingId}/date")
public class DateProposalController {

    private final IDateProposalRepository dateProposalRepository;

    @Autowired
    public DateProposalController(IDateProposalRepository dateProposalRepository) {
        this.dateProposalRepository = dateProposalRepository;
    }

    @PostMapping
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

    @PostMapping("/{proposalId}/vote")
    public void voteForDateProposal(@PathVariable String meetingId, @PathVariable String proposalId, @RequestBody String voterId) {
        dateProposalRepository.voteForDateProposal(meetingId, proposalId, voterId);
    }

    @DeleteMapping("/{proposalId}/unvote")
    public void unvoteForDateProposal(@PathVariable String meetingId, @PathVariable String proposalId, @RequestBody String voterId) {
        dateProposalRepository.unvoteForDateProposal(meetingId, proposalId, voterId);
    }

//    @GetMapping
//    public List<DateProposal> getDateProposals(@PathVariable String meetingId) {
//        return dateProposalRepository.findByMeetingId(meetingId);
//    }
}
