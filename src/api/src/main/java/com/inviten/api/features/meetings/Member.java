package com.inviten.api.features.meetings;

import com.inviten.api.generator.NameGenerator;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;



@DynamoDbBean
public class Member {

    private String phoneNumber; // też ma być zahaszowany
    private String role;
    private  String nick;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber ;
    }

    public String getRole() { return role; }

    public void setRole(String role ) {this.role = role; }

    public String getNick() { return nick; }

    public void setNick( String nick ) { this.nick = nick; }

}

