package com.example.supervet.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Appointment {

    @Id
    private LocalDateTime date;
    private String duration;
    private String reason;

    @ManyToMany
    @JoinTable(
            name = "appointment_doctor",
            joinColumns = @JoinColumn(name = "appointment_date"),
            inverseJoinColumns = @JoinColumn(name = "doctor_userName"))
    private List<Doctor> doctors;

    @ManyToMany
    @JoinTable(
            name = "appointment_patient",
            joinColumns = @JoinColumn(name = "appointment_date"),
            inverseJoinColumns = @JoinColumn(name = "patient_userName"))
    private List<Patient> patients;



    public Appointment(){}

    public Appointment(LocalDateTime date, String duration, String reason) {
        this.date = date;
        this.duration = duration;
        this.reason = reason;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
