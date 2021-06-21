package com.example.supervet.repository;

import com.example.supervet.model.entity.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface PatientRepository  extends CrudRepository<Patient, String> {

//    @Transactional
//    @Query(value = "SELECT * FROM Patient p, User u WHERE u.email = ?1",
//            nativeQuery = true)
    Optional<Patient> findByEmail(String email);

}
