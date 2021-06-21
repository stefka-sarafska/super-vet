package com.example.supervet.controller;

import com.example.supervet.exceptions.ElementNotFoundException;
import com.example.supervet.model.dto.BookPatientAppointmentDTO;
import com.example.supervet.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping(path = "/api/book/appointment")
    public ResponseEntity<Object> bookAppointment(@RequestBody BookPatientAppointmentDTO bookPatientAppointmentDTO){
        try{
            patientService.bookAppointment(bookPatientAppointmentDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (ElementNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
