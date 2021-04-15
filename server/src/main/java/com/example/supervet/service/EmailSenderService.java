package com.example.supervet.service;

import com.example.supervet.entity.Doctor;
import com.example.supervet.repository.EmailSenderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService implements EmailSenderRepository {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(Doctor doctor) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("steffiito134@gmail.com");
        msg.setSubject("SuperVet Veterinary Request");
        msg.setText("Veterinary Doctor request to be added in the platform SuperVet: \n "+doctor.toString());
        try {
            javaMailSender.send(msg);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
