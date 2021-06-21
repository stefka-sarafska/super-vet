package com.example.supervet.repository;

import com.example.supervet.exceptions.ElementNotFoundException;
import com.example.supervet.model.entity.Doctor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

    List<Doctor> findAll();

    List<Doctor> findByOrderByAddress();

    List<Doctor> findByOrderBySpecialty();

    List<Doctor> findByAddressContains(String address);

    @Query(value = "SELECT d  FROM Doctor d WHERE d.firstName = ?1 and d.middleName = ?2 and d.lastName = ?3")
    List<Doctor> findAllByFullName(String firstName,String middleName,String lastName);

    @Query(value = "SELECT d  FROM Doctor d WHERE d.firstName = ?1 and d.lastName = ?2")
    List<Doctor> findAllByFirstAndLastName(String firstName,String lastName);

    @Query(value = "SELECT d  FROM Doctor d WHERE d.firstName = ?1")
    List<Doctor> findAllByFirstName(String firstName);

    List<Doctor> findBySpecialty(String specialty);

    @Query(value = "SELECT d FROM Doctor d  WHERE d.specialty = ?1 and d.address = ?2")
    List<Doctor> findAllBySpecialtyAndAddress(String specialty, String address);

    @Query(value = "SELECT d FROM Doctor d  WHERE d.address = ?1 and d.firstName = ?2 and d.middleName = ?3 and d.lastName= ?4")
    List<Doctor> findAllByAddressAndName(String address, String firstName,String middleName,String lastName);

    @Query(value = "SELECT d FROM Doctor d  WHERE d.specialty = ?1 and d.firstName = ?2 and d.middleName = ?3 and d.lastName= ?4")
    List<Doctor> findAllBySpecialtyAndName(String address, String firstName,String middleName,String lastName);

    @Query(value = "SELECT d FROM Doctor d  WHERE d.specialty = ?1 and d.address = ?2 and d.firstName = ?3 and d.middleName = ?4 and d.lastName= ?5")
    List<Doctor> findAllBySpecialtyAddressAndName(String specialty,String address, String firstName,String middleName,String lastName);

    @Query(value = "SELECT d FROM Doctor d WHERE d.email = ?1")
    Doctor findByEmail(String email) throws ElementNotFoundException;




}
