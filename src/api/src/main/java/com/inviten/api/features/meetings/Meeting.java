<<<<<<< HEAD
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

    //
}

=======
package com.inviten.api.features.meetings;


import com.inviten.api.features.meetings.placeProposal.Place;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalTime;


@DynamoDbBean
public class Meeting {
    private String id;

    private String name;
    private LocalDate date;
    private LocalTime time;
    private List<Member> participants;
<<<<<<< HEAD
=======
    private List<DateProposal> dateProposals;
>>>>>>> a481e6c (Refactoring)
    private List<Place> placeProposals;

    private boolean isPlaceChosen = false;
    private boolean isDateChosen = false;

    private Place place;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){ return name; }

    public void setName(String name) { this.name = name; }
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

    public List<Member> getParticipants() { return participants; }
    public void setParticipants(List<Member> participants) {
        this.participants = participants;
    }

    public List<Place> getPlaceProposals() {return placeProposals;}
    public void setPlaceProposals(List<Place> placeProposals) {
        this.placeProposals = placeProposals;
    }

    public void setIsPlaceChosen(boolean isPlaceChosen) {
        this.isPlaceChosen = isPlaceChosen;
    }

    public boolean getIsPlaceChosen(){
        return isPlaceChosen;
    }

    public Place getPlace() {return place; }

    public void setPlace(Place place) {this.place = place;}
}
<<<<<<< HEAD
>>>>>>> c1f624b (implementation of addMember and deleteMember functions)
=======

>>>>>>> 20c175c (Implement placeProposal)
