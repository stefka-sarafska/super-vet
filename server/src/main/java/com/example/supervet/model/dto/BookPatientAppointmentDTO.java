package com.example.supervet.model.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookPatientAppointmentDTO {

    private LocalDateTime date;
    private String doctorEmail;
    private String reason;
    private String userEmail;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
}
