package com.example.supervet.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginUserDTO {

    @NotNull(message = "Invalid email and/or password!")
    private String email;

    @NotNull(message = "Invalid email and/or password!")
    private String password;


}
