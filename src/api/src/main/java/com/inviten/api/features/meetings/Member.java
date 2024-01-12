<<<<<<< HEAD
<<<<<<< HEAD
package com.inviten.api.features.meetings;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class Member {

    private String phoneNumber;
    private String role ;
=======
package com.inviten.api.features.meetings;

import com.inviten.api.generator.NameGenerator;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;



@DynamoDbBean
public class Member {

    private String phoneNumber; // też ma być zahaszowany
    private String role;
    private  String nick;

>>>>>>> 0508e92 (change invite methid)

    public String getPhoneNumber() {
        return phoneNumber;
    }

<<<<<<< HEAD
    public void setPhoneNumber(String id) {
        this.phoneNumber = phoneNumber ;
    }

   public String getRole() { return role; }

    public void setRole(String role ) {this.role = role; }

}

=======
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

>>>>>>> c1f624b (implementation of addMember and deleteMember functions)
=======
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber ;
    }

    public String getRole() { return role; }

    public void setRole(String role ) {this.role = role; }

    public String getNick() { return nick; }

    public void setNick( String nick ) { this.nick = nick; }

}

>>>>>>> 0508e92 (change invite methid)
