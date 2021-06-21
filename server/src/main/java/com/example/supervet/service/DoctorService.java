package com.example.supervet.service;

import com.example.supervet.exceptions.ElementAlreadyExistsException;
import com.example.supervet.exceptions.ElementNotFoundException;
import com.example.supervet.exceptions.ErrorCreatingEntityException;
import com.example.supervet.exceptions.NotValidEmailException;
import com.example.supervet.model.dto.GetDoctorRequestDTO;
import com.example.supervet.model.dto.RegisterDoctorDTO;
import com.example.supervet.model.entity.Appointment;
import com.example.supervet.model.entity.AppointmentPrimaryKey;
import com.example.supervet.model.entity.Doctor;
import com.example.supervet.model.entity.ImageModel;
import com.example.supervet.model.enumaration.Role;
import com.example.supervet.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.example.supervet.common.Constants.*;

@Component
public class DoctorService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);

    private static final List<RegisterDoctorDTO> doctorRequests = new LinkedList<>();

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ImageModelRepository imageModelRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailSenderRepository emailSenderRepository;


    public List<GetDoctorRequestDTO> getDoctorRequests() {
        List<GetDoctorRequestDTO> requests = new LinkedList<>();
        doctorRequests.forEach(request -> {
            try {
                requests.add(convertToGetDoctorRequest(request));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        });
        return requests;
    }


    public GetDoctorRequestDTO addDoctorRequest(RegisterDoctorDTO registerDoctorDTO) throws IOException {
        if (userRepository.existsByEmail(registerDoctorDTO.getEmail())) {
            throw new ElementAlreadyExistsException("There is another doctor with this email address!");
        }
        if (!registerDoctorDTO.getPassword().equals(registerDoctorDTO.getRepeatPassword())) {
            throw new ErrorCreatingEntityException("Passwords do not match!");
        }
        GetDoctorRequestDTO doctorRequest = convertToGetDoctorRequest(registerDoctorDTO);
        doctorRequests.add(registerDoctorDTO);
        return doctorRequest;
    }



    public void uploadImage(String doctorEmail, MultipartFile file) {
        RegisterDoctorDTO matchedRequest = null;
        for (RegisterDoctorDTO request : doctorRequests) {
            if (request.getEmail().equals(doctorEmail)) {
                matchedRequest = request;
                break;
            }
        }
        try {
            ImageModel img = ImageModel.builder()
                    .name(doctorEmail)
                    .type(file.getContentType())
                    .picByte(file.getBytes())
                    .build();
            if(matchedRequest!=null){
                matchedRequest.setImage(img);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    public void addDoctor(GetDoctorRequestDTO doctorRequest) throws NotValidEmailException, IOException {
        RegisterDoctorDTO matchedRequest = null;
        for (RegisterDoctorDTO request : doctorRequests) {
            if (request.getEmail().equals(doctorRequest.getEmail())) {
                matchedRequest = request;
                break;
            }
        }
        if (matchedRequest != null) {
            Doctor doctor = Doctor.builder()
                    .email(matchedRequest.getEmail())
                    .password(passwordEncoder().encode(matchedRequest.getPassword()))
                    .firstName(matchedRequest.getFirstName())
                    .middleName(matchedRequest.getMiddleName())
                    .lastName(matchedRequest.getLastName())
                    .phoneNumber(matchedRequest.getPhoneNumber())
                    .role(Role.DOCTOR.getValue())
                    .specialty(matchedRequest.getSpecialty())
                    .address(matchedRequest.getAddress())
                    .description(matchedRequest.getDescription())
                    .formation(matchedRequest.getFormation())
                    .qualifications(matchedRequest.getQualifications())
                    .biography(matchedRequest.getBiography())
                    .image(ImageModel.builder()
                            .type(matchedRequest.getImage().getType())
                            .name(matchedRequest.getImage().getName())
                            .picByte(matchedRequest.getImage().getPicByte())
                            .build())
                    .build();
            List<Appointment> generatedAppointments = generateAppointments(doctor);
            doctor.setAppointments(generatedAppointments);
            doctorRepository.save(doctor);
            doctorRequests.remove(matchedRequest);
            emailSenderRepository.sendEmailApprovedToDoctor(doctorRequest.getEmail());
        }
    }

    public void deleteDoctor(GetDoctorRequestDTO doctorRequest) {
        for (RegisterDoctorDTO request : doctorRequests) {
            if (request.getEmail().equals(doctorRequest.getEmail())) {
                doctorRequests.remove(request);
                emailSenderRepository.sendEmailDeniedToDoctor(request.getEmail());
                break;
            }
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> foundDoctors = doctorRepository.findAll();
        if (!foundDoctors.isEmpty()) {
            return foundDoctors;
        }
        throw new ElementNotFoundException("There is no doctors!");
    }

    public Doctor getDoctorByEmail(String email) throws ElementNotFoundException {
        Doctor doctor = doctorRepository.findByEmail(email);
        if (doctor != null) {
            return doctor;
        }
        throw new ElementNotFoundException("Doctor with email " + email + " does not exist!");
    }

    public List<Doctor> getDoctorsByAddress(String address) {
        List<Doctor> foundDoctors = doctorRepository.findByAddressContains(address);
        if (!foundDoctors.isEmpty()) {
            return foundDoctors;
        }
        throw new ElementNotFoundException("There is no doctors with address: " + address);
    }

    public List<Doctor> getDoctorsByFullName(String name) {
        Map<String, String> doctorNames = getDoctorNames(name);
        List<Doctor> foundDoctors=new ArrayList<>();
        if(doctorNames.size()==3){
            List<Doctor> doctors = doctorRepository.findAllByFullName(doctorNames.get(FIRST_NAME), doctorNames.get(MIDDLE_NAME), doctorNames.get(LAST_NAME));
            foundDoctors.addAll(doctors);
        }else if(doctorNames.size()==2){
            List<Doctor> doctors = doctorRepository.findAllByFirstAndLastName(doctorNames.get(FIRST_NAME), doctorNames.get(LAST_NAME));
            foundDoctors.addAll(doctors);
        }else if(doctorNames.size()==1){
            List<Doctor> doctors = doctorRepository.findAllByFirstName(doctorNames.get(FIRST_NAME));
            foundDoctors.addAll(doctors);
        }
        if (!foundDoctors.isEmpty()) {
            return foundDoctors;
        }
        throw new ElementNotFoundException("There is no doctors with name: " + name);

    }

    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);
        if (!doctors.isEmpty()) {
            return doctors;
        }
        throw new ElementNotFoundException("There is no doctors with specialty: " + specialty);
    }

    public List<Doctor> getAllDoctorsBySpecialtyAndAddress(String specialty, String address) {
        List<Doctor> doctors = doctorRepository.findAllBySpecialtyAndAddress(specialty, address);
        if (!doctors.isEmpty()) {
            return doctors;
        }
        throw new ElementNotFoundException("There is no doctors with address: " + address + " and specialty: " + specialty);
    }

    public List<Doctor> getDoctorsByAddressAndName(String address, String name) throws ElementNotFoundException {
        Map<String, String> doctorNames = getDoctorNames(name);
        List<Doctor> doctors = doctorRepository.findAllByAddressAndName(address, doctorNames.get(FIRST_NAME), doctorNames.get(MIDDLE_NAME), doctorNames.get(LAST_NAME));
        if (!doctors.isEmpty()) {
            return doctors;
        }
        throw new ElementNotFoundException("There is no doctors with address: " + address + " and name: " + name);
    }

    public List<Doctor> getDoctorsBySpecialtyAndName(String specialty, String name) throws ElementNotFoundException {
        Map<String, String> doctorNames = getDoctorNames(name);
        List<Doctor> doctors = doctorRepository.findAllBySpecialtyAndName(specialty, doctorNames.get(FIRST_NAME), doctorNames.get(MIDDLE_NAME), doctorNames.get(LAST_NAME));
        if (!doctors.isEmpty()) {
            return doctors;
        }
        throw new ElementNotFoundException("There is no doctors with specialty: " + specialty + " and name: " + name);
    }

    public List<Doctor> getDoctorsBySpecialtyAddressAndName(String specialty, String address, String name) throws ElementNotFoundException {
        Map<String, String> doctorNames = getDoctorNames(name);
        List<Doctor> doctors = doctorRepository.findAllBySpecialtyAddressAndName(specialty, address, doctorNames.get(FIRST_NAME), doctorNames.get(MIDDLE_NAME), doctorNames.get(LAST_NAME));
        if (!doctors.isEmpty()) {
            return doctors;
        }
        throw new ElementNotFoundException("There is no doctors with specialty: " + specialty + ", address: " + address + " and name: " + name);
    }


    public List<Doctor> getAllDoctorsSortedByAddress() {
        List<Doctor> doctors = doctorRepository.findByOrderByAddress();
        if (!doctors.isEmpty()) {
            return doctors;
        }
        throw new ElementNotFoundException("There is no doctors.");
    }

    public List<Doctor> getAllDoctorsSortedBySpecialty() {
        List<Doctor> doctors = doctorRepository.findByOrderBySpecialty();
        if (!doctors.isEmpty()) {
            return doctors;
        }
        throw new ElementNotFoundException("There is no doctors.");
    }

    public Map<String, String> getDoctorNames(String fullName) {
        List<String> names = Arrays.asList(fullName.split(" "));
        Map<String, String> splitNames = new HashMap<>();
        if (names.size() == 4) {
            splitNames.put(FIRST_NAME, names.get(0) + " " + names.get(1));
            splitNames.put(MIDDLE_NAME, names.get(2));
            splitNames.put(LAST_NAME, names.get(3));
        } else if(names.size()==3){
            splitNames.put(FIRST_NAME, names.get(0));
            splitNames.put(MIDDLE_NAME, names.get(1));
            splitNames.put(LAST_NAME, names.get(2));
        }else if (names.size()==2){
            splitNames.put(FIRST_NAME, names.get(0));
            splitNames.put(LAST_NAME, names.get(1));
        }else if(names.size()==1){
            splitNames.put(FIRST_NAME, names.get(0));
        }
        return splitNames;
    }

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private GetDoctorRequestDTO convertToGetDoctorRequest(RegisterDoctorDTO registerDoctorDTO) throws IOException {
        return GetDoctorRequestDTO.builder()
                .firstName(registerDoctorDTO.getFirstName())
                .middleName(registerDoctorDTO.getMiddleName())
                .lastName(registerDoctorDTO.getLastName())
                .email(registerDoctorDTO.getEmail())
                .phoneNumber(registerDoctorDTO.getPhoneNumber())
                .specialty(registerDoctorDTO.getSpecialty())
                .address(registerDoctorDTO.getAddress())
                .description(registerDoctorDTO.getDescription())
                .formation(registerDoctorDTO.getFormation())
                .qualifications(registerDoctorDTO.getQualifications())
                .biography(registerDoctorDTO.getBiography())
                .build();
    }

    private List<Appointment> generateAppointments(Doctor doctor) {
        List<Appointment> appointments = new LinkedList<>();
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime lastHalf = time.truncatedTo(ChronoUnit.HOURS)
                .plusMinutes(30 * (time.getMinute() / 30) + 30);
        while (lastHalf.isBefore(time.plusDays(5))) {
            LocalTime morningTime = LocalTime.of(8, 59, 59);
            LocalTime nightTime = LocalTime.of(17, 29, 59);
            if (lastHalf.toLocalTime().isBefore(nightTime) && lastHalf.toLocalTime().isAfter(morningTime)
                    && !lastHalf.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !lastHalf.getDayOfWeek().equals(DayOfWeek.SUNDAY )) {
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
