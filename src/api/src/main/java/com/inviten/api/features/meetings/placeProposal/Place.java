package com.inviten.api.features.meetings.placeProposal;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import java.util.List;
import java.util.ArrayList;

@DynamoDbBean
public class Place {
    private String id;

    private String name;

    private String address;

    private String description;

    private String note;
    // private User proposedBy; + getter i setter

    private List<String> votes = new ArrayList<>();

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public List<String> getVotes() { return votes; }

    public void setVotes(List<String> votes) { this.votes = votes; }
}
