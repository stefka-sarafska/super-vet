package com.example.supervet.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor extends User {

    private String specialty;
    private String address;
    @Column(length = Integer.MAX_VALUE)
    private String description;
    @Column(length = Integer.MAX_VALUE)
    private String formation;
    @Column(length = Integer.MAX_VALUE)
    private String qualifications;
    @Column(length = Integer.MAX_VALUE)
    private String biography;

    @OneToMany(mappedBy = "appointmentId.doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_name", referencedColumnName = "name")
    private ImageModel image;

    @Builder
    public Doctor(String email, String password, String firstName, String middleName, String lastName, String phoneNumber, String role, String specialty, String address, String description, String formation, String qualifications, String biography, List<Appointment> appointments,ImageModel image) {
        super(email, password, firstName, middleName, lastName, phoneNumber, role);
        this.specialty = specialty;
        this.address = address;
        this.description = description;
        this.formation = formation;
        this.qualifications = qualifications;
        this.biography = biography;
        this.appointments = appointments;
        this.image=image;
    }


}
