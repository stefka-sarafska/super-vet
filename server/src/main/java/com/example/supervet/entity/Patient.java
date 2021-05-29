package com.example.supervet.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Patient extends Person{

    @ManyToMany(mappedBy="patients")
    private Set<Appointment> appointments;

    public Patient(){}

    public Patient(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Patient(String email, String password, String firstName, String middleName, String lastName, String phoneNumber, Set<Appointment> appointments) {
        super(email, password, firstName, middleName, lastName, phoneNumber);
        this.appointments = appointments;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
