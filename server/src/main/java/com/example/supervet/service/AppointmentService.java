package com.example.supervet.service;

import com.example.supervet.exceptions.ElementNotFoundException;
import com.example.supervet.model.dto.AppointmentDTO;
import com.example.supervet.model.dto.GetDoctorAppointmentDTO;
import com.example.supervet.model.dto.GetPatientAppointmentDTO;
import com.example.supervet.model.entity.Appointment;
import com.example.supervet.model.entity.AppointmentPrimaryKey;
import com.example.supervet.model.entity.Doctor;
import com.example.supervet.model.entity.Patient;
import com.example.supervet.model.enumaration.Reason;
import com.example.supervet.repository.AppointmentRepository;
import com.example.supervet.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Scheduled(cron = "0 0 18 * * *")
    public void deleteExpiredAppointments() {
        appointmentRepository.deleteByDateBefore(LocalDateTime.now());

    }

    @Scheduled(cron = "0 0 18 * * *")
    public void generateDoctorAppointment() {
        doctorRepository.findAll().forEach(doctor -> {
            try {
                if (getFreeDoctorAppointments(doctor.getEmail()).isEmpty() && getBookedAppointmentsByDoctorEmail(doctor.getEmail()).isEmpty()) {
                }
            } catch (ElementNotFoundException e) {
                List<Appointment>  generatedAppointments= generateNewDoctorAppointments(doctor);
                doctor.setAppointments(generatedAppointments);
                doctorRepository.save(doctor);
            }
        });
    }

    public List<AppointmentDTO> getFreeDoctorAppointments(String doctorEmail) {
        List<AppointmentDTO> appointments = new ArrayList<>();
        appointmentRepository.findByAppointmentId_doctor_emailAndFreeOrderByAppointmentId_date(doctorEmail, true)
                .forEach(appointment -> {
                    AppointmentDTO foundAppointment = AppointmentDTO.builder()
                            .free(appointment.getFree())
                            .date(appointment.getAppointmentId().getDate())
                            .doctorEmail(doctorEmail)
                            .build();
                    appointments.add(foundAppointment);
                });
        if (!appointments.isEmpty()) {
            return appointments;
        }
        throw new ElementNotFoundException("Doctor with email " + doctorEmail + " does not have free appointments.");

    }

    public List<GetDoctorAppointmentDTO> getBookedAppointmentsByDoctorEmail(String doctorEmail) {
        List<GetDoctorAppointmentDTO> appointments = new ArrayList<>();
        appointmentRepository.findByAppointmentId_doctor_emailAndFreeOrderByAppointmentId_date(doctorEmail, false)
                .forEach(appointment -> {
                    Patient patient = appointment.getPatient();
                    GetDoctorAppointmentDTO foundAppointment = GetDoctorAppointmentDTO.builder()
                            .patientEmail(patient.getEmail())
                            .patientPhone(patient.getPhoneNumber())
                            .patientFirstName(patient.getFirstName())
                            .patientLastName(patient.getLastName())
                            .date(appointment.getAppointmentId().getDate())
                            .reason(appointment.getReason().getValue())
                            .build();
                    appointments.add(foundAppointment);
                });
        if (!appointments.isEmpty()) {
            return appointments;
        }
        throw new ElementNotFoundException("Doctor with email " + doctorEmail + " does not have booked appointments.");
    }

    public AppointmentDTO getEarlierDoctorAppointment(String doctorEmail) throws ElementNotFoundException {
        LocalDateTime earlierDoctorAppointmentDate = appointmentRepository.getEarlierDoctorAppointmentDate(doctorEmail, true);
        Optional<Appointment> appointment = appointmentRepository.findByAppointmentId(earlierDoctorAppointmentDate, doctorEmail);
        if (appointment.isPresent()) {
            return AppointmentDTO.builder()
                    .date(appointment.get().getAppointmentId().getDate())
                    .doctorEmail(appointment.get().getAppointmentId().getDoctor().getEmail())
                    .build();
        }
        throw new ElementNotFoundException("Doctor does not have free appointments");

    }

    public List<GetPatientAppointmentDTO> getAppointmentsByPatientEmail(String email) {
        List<GetPatientAppointmentDTO> appointments = new LinkedList<>();
        appointmentRepository.findByPatient_emailAndFreeOrderByAppointmentId_date(email, false).forEach(appointment -> {
            Doctor doctor = appointment.getAppointmentId().getDoctor();
            GetPatientAppointmentDTO foundAppointment = GetPatientAppointmentDTO.builder()
                    .doctorEmail(doctor.getEmail())
                    .date(appointment.getAppointmentId().getDate())
                    .reason(appointment.getReason().getValue())
                    .doctorFirstName(doctor.getFirstName())
                    .doctorLastName(doctor.getLastName())
                    .doctorAddress(doctor.getAddress())
                    .doctorPhone(doctor.getPhoneNumber())
                    .build();
            appointments.add(foundAppointment);
        });
        if (!appointments.isEmpty()) {
            return appointments;
        }
        throw new ElementNotFoundException("Patient does not have appointments!");
    }

    public List<String> getAppointmentReasons() {
        List<String> reasons = new LinkedList<>();
        for (Reason reason : Reason.values()) {
            reasons.add(reason.getValue());
        }
        return reasons;
    }

    private List<Appointment> generateNewDoctorAppointments(Doctor doctor) {
        List<Appointment> appointments = new LinkedList<>();
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime lastHalf = time.truncatedTo(ChronoUnit.HOURS)
                .plusMinutes(30 * (time.getMinute() / 30) + 30);
        while (lastHalf.isBefore(time.plusDays(5))) {
            LocalTime morningTime = LocalTime.of(8, 59, 59);
            LocalTime nightTime = LocalTime.of(17, 29, 59);
            if (lastHalf.toLocalTime().isBefore(nightTime) && lastHalf.toLocalTime().isAfter(morningTime)
                    && !lastHalf.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !lastHalf.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                lastHalf = lastHalf.plusMinutes(30);
                Appointment newAppointment = Appointment.builder()
                        .appointmentId(AppointmentPrimaryKey.builder()
                                .date(lastHalf)
                                .doctor(doctor)
                                .build())
                        .free(true)
                        .build();
                appointments.add(newAppointment);
            } else {
                lastHalf = lastHalf.plusHours(15);
                lastHalf = lastHalf.plusMinutes(30);
            }
        }
        return appointments;
    }
}
