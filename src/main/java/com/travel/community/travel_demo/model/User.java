package com.travel.community.travel_demo.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String userName;
    private String userPassword;
    private String accountId;
    private String userTelephone;
    private String userEmail;
    private String token;
    private String gmtCreate;
    private Integer userType;
    private String avatarUrl;
}
