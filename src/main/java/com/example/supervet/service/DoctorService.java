package com.example.supervet.service;

import com.example.supervet.entity.Doctor;
import com.example.supervet.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;
import java.util.Set;

@Component
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

   public void addDoctor(Doctor doctor){
       doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors(){
       List<Doctor> foundDoctors=doctorRepository.findAll();
       if(!foundDoctors.isEmpty()){
           return foundDoctors;
       }
       return null;
    }

    public List<Doctor> getDoctorsByAddress(String address){
       List<Doctor> foundDoctors=doctorRepository.findByAddressContains(address);
        if(!foundDoctors.isEmpty()){
            return foundDoctors;
        }
        return null;
    }

    public List<Doctor> getDoctorsByFullName(String firstName,String middleName,String lastName){
       List<Doctor> doctors=doctorRepository.findAllByFullName(firstName,middleName,lastName);
        if(!doctors.isEmpty()){
            return doctors;
        }
        return null;
    }

}
