package com.example.supervet.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AppointmentPrimaryKey implements Serializable {

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    @JsonIgnore
    private Doctor doctor;

}
