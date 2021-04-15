package com.example.supervet.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Patient extends Person{

    @ManyToMany(mappedBy="patients")
    private Set<Appointment> appointments;

    public Patient(){}

    public Patient(String userName, String password, String firstName, String middleName, String lastName, String phoneNumber, Set<Appointment> appointments) {
        super(userName, password, firstName, middleName, lastName, phoneNumber);
        this.appointments = appointments;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
