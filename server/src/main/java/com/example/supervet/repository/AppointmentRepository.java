package com.example.supervet.repository;

import com.example.supervet.model.entity.Appointment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    List<Appointment> findByAppointmentId_doctor_emailAndFreeOrderByAppointmentId_date(String email, boolean free);

    List<Appointment> findByPatient_emailAndFreeOrderByAppointmentId_date(String email,boolean free);

    @Transactional
    @Query(value = "SELECT a FROM Appointment a WHERE a.appointmentId.date = ?1 and a.appointmentId.doctor.email = ?2")
    Optional<Appointment> findByAppointmentId(LocalDateTime date, String email);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Appointment a WHERE a.appointmentId.date < ?1")
    void deleteByDateBefore(LocalDateTime date);

    @Query(value = "SELECT MIN(a.appointmentId.date) FROM Appointment a WHERE a.appointmentId.doctor.email = ?1 and a.free = ?2")
    LocalDateTime getEarlierDoctorAppointmentDate(String doctorEmail, boolean free);


}
