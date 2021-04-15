package com.example.supervet.repository;

import com.example.supervet.entity.Doctor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

    List<Doctor> findAll();

    List<Doctor> findByAddressContains(String address);

    @Query(value = "SELECT * FROM Doctor d  WHERE d.first_name = ?1 and d.middle_name = ?2 and d.last_name = ?3",
    nativeQuery = true)
    List<Doctor> findAllByFullName(String firstName,String middleName,String lastName);
}
