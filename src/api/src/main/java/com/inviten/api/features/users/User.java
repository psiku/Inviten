package com.inviten.api.features.users;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;


import java.util.List;

@DynamoDbBean
public class User {

    private String phoneNumber; // tutaj zahaszowany ma być

    private List<String> MeetingsIds;

    @DynamoDbPartitionKey
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public List<String> getMeetingsIds() {
        return MeetingsIds;
    }

    public void setMeetingsIds(List<String> meetingsIds) {
        MeetingsIds = meetingsIds;
    }
}
