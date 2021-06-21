package com.example.supervet.model.dto;

import com.example.supervet.model.enumaration.Reason;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDTO {

    private String doctorEmail;
    private LocalDateTime date;
    private Boolean free;
    private String reason;

}
