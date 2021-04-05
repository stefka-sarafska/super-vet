package com.example.supervet.repository;

import com.example.supervet.entity.Doctor;

public interface EmailSenderRepository {

    void sendEmail(Doctor doctor);
}
