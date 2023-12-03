package com.inviten.api.features.meetings;


import com.inviten.api.features.meetings.dateProposal.DateProposal;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalTime;


@DynamoDbBean
public class Meeting {
    private String id;
    private LocalDate date;
    private LocalTime time;
    private List<Member> participants;
    private List<DateProposal> dateProposals;
    private boolean isDateChosen = false;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {this.date = date;}

    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<Member> getParticipants() {return participants;}
    public void setParticipants(List<Member> participants) {
        this.participants = participants;
    }

    public List<DateProposal> getDateProposals() {
        return dateProposals;
    }

    public void setDateProposals(List<DateProposal> dateProposals) {
        this.dateProposals = dateProposals;
    }

    public void setIsDateChosen(boolean isDateChosen) {
        this.isDateChosen = isDateChosen;
    }

    public boolean getIsDateChosen(){
        return isDateChosen;
    }
}

