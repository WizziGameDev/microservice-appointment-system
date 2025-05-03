package com.appointment.appointment_service.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleDoctorResponse {
    private String slug;
    private String name;
    private String specialization;
}
