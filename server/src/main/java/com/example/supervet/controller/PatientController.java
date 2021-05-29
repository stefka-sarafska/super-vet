package com.example.supervet.controller;

import com.example.supervet.entity.Patient;
import com.example.supervet.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping(path = "/add/patient")
    @ResponseStatus(value = HttpStatus.OK)
    public void registerPatient(@RequestBody Patient patient){
        patientService.addPatient(patient);
    }
}
