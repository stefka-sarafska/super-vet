package com.example.supervet.service;

import com.example.supervet.entity.Patient;
import com.example.supervet.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;


    public void addPatient(Patient patient){
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        Patient patient1=new Patient();
//        patient1.setEmail(patient.getEmail());
//        patient1.setPassword(passwordEncoder.encode(patient.getPassword()));
//        patient1.setFirstName(patient.getFirstName());
//        patient1.setMiddleName(patient.getMiddleName());
//        patient1.setLastName(patient.getLastName());
//        patient1.setPhoneNumber(patient.getPhoneNumber());
        patientRepository.save(patient);
    }
}
