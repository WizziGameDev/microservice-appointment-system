package com.appointment.appointment_service.feign.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {

    private String slug;

    private String name;

    private String email;

    private Long phoneNumber;

    private String address;

    private String gender;

    private Long birthDate;

    private String licenseNumber;

    private Integer experienceYears;

    private String specialization;

    private List<AvailabilityResponse> availabilities;
}
