package com.inviten.api.authorization.model.response;

public class LoginRes {
    private String phoneNumber;
    private String token;

    private String tokenValidity;

    public LoginRes(String phoneNumber, String token, String expirationTimestamp) {
        this.phoneNumber = phoneNumber;
        this.token = token;
        this.tokenValidity = expirationTimestamp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenValidity() {
        return tokenValidity;
    }

    public void setTokenValidity(String tokenValidity) {
        this.tokenValidity = tokenValidity;
    }
}

