<<<<<<< HEAD
package com.inviten.api.features.meetings;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class Member {

    private String phoneNumber;
    private String role ;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String id) {
        this.phoneNumber = phoneNumber ;
    }

   public String getRole() { return role; }

    public void setRole(String role ) {this.role = role; }

}

=======
package com.inviten.api.features.meetings;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;


@DynamoDbBean
public class Member {

    private String phoneNumber; // też ma być zahaszowany
    private String role;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber ;
    }

   public String getRole() { return role; }

    public void setRole(String role ) {this.role = role; }

}

>>>>>>> c1f624b (implementation of addMember and deleteMember functions)
