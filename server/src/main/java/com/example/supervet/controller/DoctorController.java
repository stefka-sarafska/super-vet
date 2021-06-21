package com.example.supervet.controller;

import com.example.supervet.exceptions.ElementAlreadyExistsException;
import com.example.supervet.exceptions.ElementNotFoundException;
import com.example.supervet.exceptions.ErrorCreatingEntityException;
import com.example.supervet.exceptions.NotValidEmailException;
import com.example.supervet.model.dto.GetDoctorRequestDTO;
import com.example.supervet.model.dto.RegisterDoctorDTO;
import com.example.supervet.model.entity.Doctor;
import com.example.supervet.service.DoctorService;
import com.example.supervet.service.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DoctorController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private DoctorService doctorService;


    @Autowired
    private EmailSenderService emailSenderService;


    @PostMapping(value = "/{doctorEmail}/upload")
    public ResponseEntity<Object> uploadImage(@PathVariable String doctorEmail, @RequestParam("imageFile") MultipartFile file) throws IOException {
        try {
            doctorService.uploadImage(doctorEmail, file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @RequestMapping(path = "/doctor/request")
    public ResponseEntity<Object> addDoctorRequest(@RequestBody RegisterDoctorDTO registerDoctorDTO,
                                                   Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            GetDoctorRequestDTO doctor = doctorService.addDoctorRequest(registerDoctorDTO);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } catch (ElementAlreadyExistsException | ErrorCreatingEntityException | IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/doctor/requests")
    public ResponseEntity<List<GetDoctorRequestDTO>> getDoctorRequests() {
        List<GetDoctorRequestDTO> doctorRequests = doctorService.getDoctorRequests();
        if (doctorRequests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctorRequests, HttpStatus.OK);
    }

    @PostMapping(path = "/add/doctor")
    public ResponseEntity<Object> addDoctor(@RequestBody GetDoctorRequestDTO doctor) {
        try {
            doctorService.addDoctor(doctor);
            return new ResponseEntity<>("Successfully add doctor.", HttpStatus.OK);
        } catch (NotValidEmailException | IOException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(path = "/delete/doctor")
    public ResponseEntity<Object> deleteDoctor(@RequestBody GetDoctorRequestDTO doctor) {
        try {
            doctorService.deleteDoctor(doctor);
            return new ResponseEntity<>("Successfully delete doctor.", HttpStatus.OK);
        } catch (NotValidEmailException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "/all/doctors")
    public ResponseEntity<Object> getAllDoctors() {
        try {
            List<Doctor> doctor = doctorService.getAllDoctors();
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/doctors/email/{email}")
    public ResponseEntity<Object> getDoctorByEmail(@PathVariable String email) {
        try {
            Doctor doctor = doctorService.getDoctorByEmail(email);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(path = "/all/doctors/sortedByAddress")
    public ResponseEntity<Object> getAllDoctorsSortedByAddress() {
        try {
            List<Doctor> doctors = doctorService.getAllDoctorsSortedByAddress();
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/all/doctors/sortedBySpecialty")
    public ResponseEntity<Object> getAllDoctorsSortedBySpecialty() {
        try {
            List<Doctor> doctors = doctorService.getAllDoctorsSortedBySpecialty();
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/doctors/{address}")
    public ResponseEntity<Object> getAllDoctorsByAddress(@PathVariable String address) {
        try {
            List<Doctor> doctors = doctorService.getDoctorsByAddress(address);
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/doctors/name/{name}")
    public ResponseEntity<Object> getAllDoctorsByFullName(@PathVariable String name) {
        try {
            List<Doctor> doctors = doctorService.getDoctorsByFullName(name);
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/doctors/specialty/{specialty}")
    public ResponseEntity<Object> getAllDoctorsBySpecialty(@PathVariable String specialty) {
        try {
            List<Doctor> doctors = doctorService.getDoctorsBySpecialty(specialty);
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/doctors/specialty/{specialty}/address/{address}")
    public ResponseEntity<Object> getAllDoctorsBySpecialtyAndAddress(@PathVariable String specialty, @PathVariable String address) {
        try {
            List<Doctor> doctors = doctorService.getAllDoctorsBySpecialtyAndAddress(specialty, address);
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/doctors/address/{address}/name/{name}")
    public ResponseEntity<Object> getDoctorsByAddressAndName(@PathVariable String address, @PathVariable String name) {
        try {
            List<Doctor> doctors = doctorService.getDoctorsByAddressAndName(address, name);
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/doctors/specialty/{specialty}/name/{name}")
    public ResponseEntity<Object> getDoctorsBySpecialtyAndName(@PathVariable String specialty, @PathVariable String name) {
        try {
            List<Doctor> doctors = doctorService.getDoctorsBySpecialtyAndName(specialty, name);
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/doctors/specialty/{specialty}/address/{address}/name/{name}")
    public ResponseEntity<Object> getDoctorsBySpecialtyAddressAndName(@PathVariable String specialty, @PathVariable String address, @PathVariable String name) {
        try {
            List<Doctor> doctors = doctorService.getDoctorsBySpecialtyAddressAndName(specialty, address, name);
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
