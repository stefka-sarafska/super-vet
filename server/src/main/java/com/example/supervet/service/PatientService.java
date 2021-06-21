package com.example.supervet.service;

import com.example.supervet.exceptions.ElementNotFoundException;
import com.example.supervet.model.dto.BookPatientAppointmentDTO;
import com.example.supervet.model.entity.Appointment;
import com.example.supervet.model.entity.Patient;
import com.example.supervet.repository.AppointmentRepository;
import com.example.supervet.repository.PatientRepository;
import com.example.supervet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;


    public void bookAppointment(BookPatientAppointmentDTO bookPatientAppointmentDTO) {
        Optional<Appointment> optionalAppointment = appointmentRepository
                .findByAppointmentId(bookPatientAppointmentDTO.getDate(),
                        bookPatientAppointmentDTO.getDoctorEmail());
        if (optionalAppointment.isPresent()) {
            Optional<Patient> patientOptional = patientRepository.findByEmail(bookPatientAppointmentDTO.getUserEmail());
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
                Appointment appointment = optionalAppointment.get();
                appointment.setReason(bookPatientAppointmentDTO.getReason());
                appointment.setPatient(patient);
                appointment.setFree(false);
                appointmentRepository.save(appointment);
            } else {
                throw new ElementNotFoundException("Appointment does not exist!");
            }

        }
    }


}
