package com.example.supervet.controller;

import com.example.supervet.exceptions.ElementAlreadyExistsException;
import com.example.supervet.exceptions.ElementNotFoundException;
import com.example.supervet.exceptions.ErrorCreatingEntityException;
import com.example.supervet.exceptions.UnauthorizedAccessException;
import com.example.supervet.model.dto.LoginUserDTO;
import com.example.supervet.model.dto.RegisterUserDTO;
import com.example.supervet.model.entity.User;
import com.example.supervet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionManager sessionManager;


    @PostMapping("/api/register")
    public ResponseEntity<Object> registerUser(HttpSession session,
                                               @Valid @RequestBody RegisterUserDTO registerUserDTO,
                                               Errors errors) {
        if (sessionManager.isLoggedIn(session, registerUserDTO.getEmail())) {
            return new ResponseEntity<>("You are already logged in!", HttpStatus.CONFLICT);
        }
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            User user = userService.register(registerUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (ElementAlreadyExistsException | ErrorCreatingEntityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/api/login")
    public ResponseEntity<Object> loginUser(HttpSession session,
                                            @Valid @RequestBody LoginUserDTO loginUserDTO,
                                            Errors errors) {
        if (sessionManager.isLoggedIn(session, loginUserDTO.getEmail())) {
            return new ResponseEntity<>("You are already logged in!", HttpStatus.CONFLICT);
        }
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            User user = userService.loginUser(loginUserDTO);
            sessionManager.setSessionAttributes(session, user.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (ElementNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/api/logout")
    public ResponseEntity<String> logoutUser(HttpSession session, @Valid @RequestBody LoginUserDTO loginUserDTO) {

        try {
            sessionManager.checkIfLoggedIn(session, loginUserDTO.getEmail());
            session.invalidate();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UnauthorizedAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}
