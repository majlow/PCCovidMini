package com.example.pccovidmini;

import java.sql.Date;

public class User {
    public String fullName, email, address,phoneNum, dateOfBirth,nationalID, socialInsurance;
    public User() {
    }
    public User(String fullName, String email, String phoneNum, String address, String dateOfBirth, String nationalID, String socialInsurance){
        this.fullName = fullName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.nationalID = nationalID;
        this.socialInsurance = socialInsurance;
    }

    }
