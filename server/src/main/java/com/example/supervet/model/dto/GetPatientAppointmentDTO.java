package com.example.supervet.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPatientAppointmentDTO {

    private LocalDateTime date;
    private String reason;
    private String doctorEmail;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorAddress;
    private String doctorPhone;
}
