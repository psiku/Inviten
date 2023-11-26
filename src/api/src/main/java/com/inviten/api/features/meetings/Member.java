package com.inviten.api.features.meetings;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class Member {

    private String phoneNumber;
    private Role role;

    public String getId() {
        return phoneNumber;
    }

    public void setId(String id) {
        this.phoneNumber = phoneNumber ;
    }

   public Role getRole() { return role; }

    public void setRole(Role role ) {this.role = role; }

}

