package com.inviten.api.features.meetings;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class Role {
    static String getAdminRole(){ return "admin"; }
    static String getOwnerRole() { return "owner"; }
    static String getGuestRole() { return "guest"; }
}
