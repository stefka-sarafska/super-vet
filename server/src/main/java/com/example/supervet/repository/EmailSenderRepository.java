package com.example.supervet.repository;

import com.example.supervet.model.entity.Doctor;

import java.io.IOException;

public interface EmailSenderRepository {

    void sendEmailApprovedToDoctor(String doctorEmail);

    void sendEmailDeniedToDoctor(String doctorEmail);

    void sendEmailToUser(String email);
    
}
