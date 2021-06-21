package com.example.supervet.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserDTO {

    private String email;

    public GetUserDTO(String email) {
        this.email = email;
    }


}
