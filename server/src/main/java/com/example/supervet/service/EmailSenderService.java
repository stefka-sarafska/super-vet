package com.example.supervet.service;

import com.example.supervet.model.entity.Doctor;
import com.example.supervet.model.entity.ImageModel;
import com.example.supervet.exceptions.NotValidEmailException;
import com.example.supervet.repository.EmailSenderRepository;
import com.example.supervet.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EmailSenderService implements EmailSenderRepository {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

    @Autowired
    private final JavaMailSender javaMailSender;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailApprovedToDoctor(String doctorEmail) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(doctorEmail);
            msg.setSubject("SuperVet Veterinary Request Status");
            msg.setText("Здравей, \n "+"Ти беше удобрен и вече си част от SuperVet платформата. \n"
            +"За логин можеш да използваш имейла и паролата зададени от теб при попълването на формуляра. \n"
            +"В профила си ще можеш да намериш всички запазени часове за преглед при теб. \n"+
                    "Поздрави, \n"+"Supervet Admin");
            try {
                javaMailSender.send(msg);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

    }

    public void sendEmailDeniedToDoctor(String doctorEmail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(doctorEmail);
        msg.setSubject("SuperVet Veterinary Request Status");
        msg.setText("Здравей, \n "+"За съжаление ти не беше удобрен да станеш част от платформата. \n"
                +"За повече информация можеш да отговориш на същия имейл."+
                "Поздрави, \n"+"Supervet Admin");
        try {
            javaMailSender.send(msg);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    public void sendEmailToUser(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("SuperVet Registration");
        msg.setText("Здравей, \n "+ "Ти успешно се регистрира в SuperVet платформата! В своя акаунт ще можеш да виждаш всичките си запазени " +
                "часове за преглед, при отказ от преглед моля позвани на исписания контакт!");
        try {
            javaMailSender.send(msg);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
