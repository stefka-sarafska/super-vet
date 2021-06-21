package com.example.supervet.service;

import com.example.supervet.exceptions.ElementAlreadyExistsException;
import com.example.supervet.exceptions.ElementNotFoundException;
import com.example.supervet.exceptions.ErrorCreatingEntityException;
import com.example.supervet.model.dto.LoginUserDTO;
import com.example.supervet.model.dto.RegisterUserDTO;
import com.example.supervet.model.entity.Patient;
import com.example.supervet.model.entity.User;
import com.example.supervet.model.enumaration.Role;
import com.example.supervet.repository.EmailSenderRepository;
import com.example.supervet.repository.PatientRepository;
import com.example.supervet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private EmailSenderRepository emailSenderRepository;

    public User register(RegisterUserDTO registerUserDTO) {
        if (userRepository.existsByEmail(registerUserDTO.getEmail())) {
            throw new ElementAlreadyExistsException("There is another user with this email address!");
        }
        if (!registerUserDTO.getPassword().equals(registerUserDTO.getRepeatPassword())) {
            throw new ErrorCreatingEntityException("Passwords do not match!");
        }
        if(registerUserDTO.getRole() == null){
            Patient patient = Patient.builder()
                    .email(registerUserDTO.getEmail())
                    .password(passwordEncoder().encode(registerUserDTO.getPassword()))
                    .firstName(registerUserDTO.getFirstName())
                    .middleName(registerUserDTO.getMiddleName())
                    .lastName(registerUserDTO.getLastName())
                    .phoneNumber(registerUserDTO.getPhoneNumber())
                    .role(Role.USER.getValue())
                    .appointments(null)
                    .build();
            patientRepository.save(patient);
            return patient;
        }else {
            User user = new User(registerUserDTO.getEmail(), passwordEncoder().encode(registerUserDTO.getPassword()),
                    registerUserDTO.getFirstName(), registerUserDTO.getMiddleName(), registerUserDTO.getLastName(), registerUserDTO.getPhoneNumber(), registerUserDTO.getRole().getValue());
            userRepository.save(user);
            emailSenderRepository.sendEmailToUser(user.getEmail());
            return user;
        }
    }

    public User loginUser(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail())
                .orElseThrow(() -> new ElementNotFoundException("Invalid email and/or password!"));
        if (passwordEncoder().matches(loginUserDTO.getPassword(), user.getPassword())) {
            return user;
        } else throw new ElementNotFoundException("Invalid email and/or password!");
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ElementNotFoundException("No such user found!"));
    }

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
