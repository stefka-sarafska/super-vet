package com.example.supervet.controller;

import com.example.supervet.exceptions.ElementNotFoundException;
import com.example.supervet.model.dto.AppointmentDTO;
import com.example.supervet.model.dto.GetDoctorAppointmentDTO;
import com.example.supervet.model.dto.GetPatientAppointmentDTO;
import com.example.supervet.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping(path = "/doctors/{email}/free/appointments")
    public ResponseEntity<Object> getFreeDoctorAppointments(@PathVariable String email) {
        try{
            List<AppointmentDTO> appointments = appointmentService.getFreeDoctorAppointments(email);
            return new ResponseEntity<>(appointments,HttpStatus.OK);
        }catch (ElementNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/doctors/{email}/booked/appointments")
    public ResponseEntity<Object> getBookedDoctorAppointments(@PathVariable String email) {
        try{
            List<GetDoctorAppointmentDTO> appointments = appointmentService.getBookedAppointmentsByDoctorEmail(email);
            return new ResponseEntity<>(appointments,HttpStatus.OK);
        }catch (ElementNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/patient/{email}/appointments")
    public ResponseEntity<Object> getPatientAppointments(@PathVariable String email) {
        try {
            List<GetPatientAppointmentDTO> appointments = appointmentService.getAppointmentsByPatientEmail(email);
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/doctors/{email}/earlier/appointment")
    public ResponseEntity<Object> getEarlierDoctorAppointment(@PathVariable String email) {
        try {
            AppointmentDTO appointment = appointmentService.getEarlierDoctorAppointment(email);
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/appointment/reasons")
    public ResponseEntity<List<String>> getAppointmentReasons() {
        List<String> appointments = appointmentService.getAppointmentReasons();
        if (!appointments.isEmpty()) {
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
