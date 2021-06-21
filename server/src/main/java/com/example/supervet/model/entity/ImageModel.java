package com.example.supervet.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "image_table")
public class ImageModel implements Serializable {

    @Id
    @Column(name = "name")
    private String name;


    @Column(name = "type")
    private String type;
    @Lob
    @Column(name = "picByte")
    private byte[] picByte;

    @OneToOne(mappedBy = "image")
    @JsonIgnore
    private Doctor doctor;

}
