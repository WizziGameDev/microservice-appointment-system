package com.service.patient_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {

    @NotBlank(message = "Name can't be blank")
    @Size(min = 1, max = 50, message = "Name must be under or same 50")
    private String name;

    @Email(message = "Your email not valid")
    private String email;

    @NotNull(message = "Phone Number can't be null")
    private Long phoneNumber;

    @Size(min = 1, max = 150, message = "Name must be under or same 150")
    private String address;

    private String gender;

    private Long birthDate;
}
