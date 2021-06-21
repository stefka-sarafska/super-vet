package com.example.supervet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDoctorAppointmentDTO {

    private LocalDateTime date;
    private String reason;
    private String patientEmail;
    private String patientFirstName;
    private String patientLastName;
    private String patientPhone;
}
