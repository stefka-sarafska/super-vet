package com.example.supervet.model.enumaration;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("ADMIN"), USER("USER"), DOCTOR("DOCTOR");

    String value;

    Role(String value) {
        this.value = value;
    }
}
