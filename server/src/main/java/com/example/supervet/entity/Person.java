package com.example.supervet.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Person extends User{

    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;

    public Person(){ }

    public Person(String userName, String password, String firstName, String middleName, String lastName, String phoneNumber) {
        super(userName, password);
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
