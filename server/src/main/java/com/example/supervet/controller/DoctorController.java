package com.example.supervet.controller;

import com.example.supervet.entity.Doctor;
import com.example.supervet.service.DoctorService;
import com.example.supervet.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api")
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private EmailSenderService emailSenderService;

    @RequestMapping(path = "/email")
    @ResponseStatus(value = HttpStatus.OK)
    public void sendEmail(@RequestBody Doctor doctor){
        emailSenderService.sendEmail(doctor);
    }

    @PostMapping(path = "/add/doctor")
    @ResponseStatus(value = HttpStatus.OK)
    public void addDoctor(@RequestBody Doctor doctor){
        doctorService.addDoctor(doctor);
    }

    @GetMapping(path = "/all/doctors")
    public ResponseEntity<List<Doctor>>  getAllDoctors(){
        List<Doctor> allDoctors=doctorService.getAllDoctors();
        if(allDoctors!=null){
            return new ResponseEntity<>(allDoctors,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/doctors/{address}")
    public ResponseEntity<List<Doctor>>  getAllDoctorsByAddress(@PathVariable String address){
        List<Doctor> allDoctors=doctorService.getDoctorsByAddress(address);
        if(allDoctors!=null){
            return new ResponseEntity<>(allDoctors,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/doctors")
    public ResponseEntity<List<Doctor>>  getAllDoctorsByFullName(@RequestParam String firstName,@RequestParam String middleName,@RequestParam String lastName){
        List<Doctor> doctors=doctorService.getDoctorsByFullName(firstName,middleName,lastName);
        if(doctors!=null){
            return new ResponseEntity<>(doctors,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
