package com.example.supervet.model.dto;

import com.example.supervet.model.entity.Appointment;
import com.example.supervet.model.entity.ImageModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
public class RegisterDoctorDTO extends RegisterUserDTO {

    @NotBlank(message = "Please enter your specialty!")
    private String specialty;
    @NotBlank(message = "Please enter your address!")
    private String address;
    @NotBlank(message = "Please enter your description!")
    private String description;
    @NotBlank(message = "Please enter your formation!")
    private String formation;
    @NotBlank(message = "Please enter your qualifications!")
    private String qualifications;
    @NotBlank(message = "Please enter your biography!")
    private String biography;
    private Set<Appointment> appointments;
    private ImageModel image;
//    private MultipartFile file;

}
