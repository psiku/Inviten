package com.inviten.api.features.meetings;

import com.inviten.api.features.shared.User;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import java.util.Date;
import java.util.List;
import java.time.LocalTime;


@DynamoDbBean
public class Meeting {
    private String id;
    private Date date;
    private LocalTime time;
    private List<User> participants;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {this.date = date;}

    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<User> getParticipants() {return participants;}
    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

}
