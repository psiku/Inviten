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
    void addVote(@PathVariable String meetingId, @PathVariable String proposalId){
        dateProposalRepository.addVote(meetingId, proposalId);
    }

    @PostMapping("/{proposalId}/unvote")
    void removeVote(@PathVariable String meetingId, @PathVariable String proposalId){
        dateProposalRepository.removeVote(meetingId, proposalId);
    }

    @PostMapping("/{proposalId}/schedule")
    void confirmDate(@PathVariable String meetingId, @PathVariable String proposalId){
        dateProposalRepository.confirmDate(meetingId, proposalId);
    }

//    @GetMapping
//    public List<DateProposal> getDateProposals(@PathVariable String meetingId) {
//        return dateProposalRepository.findByMeetingId(meetingId);
//    }
}
