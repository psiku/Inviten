package com.inviten.api.features.meetings.dateProposal;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@DynamoDbBean
public class DateProposal {
    private String id;
    private LocalDate proposedDate;
    private LocalTime proposedTime;
    private String proposedBy;
    private List<String> votes = new ArrayList<>();

    public DateProposal() {
    }

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(LocalDate proposedDate) {
        this.proposedDate = proposedDate;
    }

    public LocalTime getProposedTime() {
        return proposedTime;
    }

    public void setProposedTime(LocalTime proposedTime) {
        this.proposedTime = proposedTime;
    }

    public String getProposedBy() {
        return proposedBy;
    }

    public void setProposedBy(String proposedBy) {
        this.proposedBy = proposedBy;
    }

    public List<String> getVotes() {
        return votes;
    }

    public void setVotes(List<String> votes) {
        this.votes = votes;
    }

    public void addVote(String voterId) {
        this.votes.add(voterId);
    }

    public void removeVote(String voterId) {
        this.votes.remove(voterId);
    }
}
