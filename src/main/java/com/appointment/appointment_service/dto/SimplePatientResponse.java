package com.appointment.appointment_service.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimplePatientResponse {
    private String slug;
    private String name;
}

