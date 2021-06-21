package com.example.supervet.model.dto;

import com.example.supervet.model.entity.ImageModel;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDoctorRequestDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String specialty;
    private String address;
    private String description;
    private String formation;
    private String qualifications;
    private String biography;
    private ImageModel image;
}
