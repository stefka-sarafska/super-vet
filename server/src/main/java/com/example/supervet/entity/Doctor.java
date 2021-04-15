package com.example.supervet.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Doctor {


    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String specialty;
    private String address;
    @Id
    private String email;

    @ManyToMany(mappedBy="doctors")
    private Set<Appointment> appointments;

    public Doctor(String firstName, String middleName, String lastName, String phoneNumber, String specialty, String address, String email, Set<Appointment> appointments) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.specialty = specialty;
        this.address = address;
        this.email = email;
        this.appointments = appointments;
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Veterinary Doctor " +
                "firstName:" + firstName + '\n' +
                ", middleName:" + middleName + '\n' +
                ", lastName:" + lastName + '\n' +
                ", phoneNumber:" + phoneNumber + '\n' +
                ", specialty:" + specialty + '\n' +
                ", address:" + address + '\n' +
                ", email:" + email + '\n' +
                '}';
    }
}
