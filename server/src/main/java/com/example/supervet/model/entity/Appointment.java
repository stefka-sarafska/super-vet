package com.example.supervet.model.entity;

import com.example.supervet.model.enumaration.Reason;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @EmbeddedId
    private AppointmentPrimaryKey appointmentId;

    private Boolean free;
    @Enumerated(EnumType.STRING)
    private Reason reason;

    @ManyToOne
    @JoinColumn(name="patient_id")
    @JsonIgnore
    private Patient patient;

    public void setReason(String reason) {
        for (Reason r : Reason.values()) {
            if (reason.equals(r.getName())) {
                this.reason = r;
                break;
            }
        }
    }

}
