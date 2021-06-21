package com.example.supervet.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Patient extends User {

    @OneToMany(mappedBy = "patient",cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Appointment> appointments;

    @Builder
    public Patient(String email, String password, String firstName, String middleName, String lastName, String phoneNumber, String role, Set<Appointment> appointments) {
        super(email, password, firstName, middleName, lastName, phoneNumber, role);
        this.appointments = appointments;
    }
}
