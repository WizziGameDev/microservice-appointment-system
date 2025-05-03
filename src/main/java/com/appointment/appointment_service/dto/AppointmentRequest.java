package com.appointment.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {
    private String nameAppointment;
    private String patientSlug;
    private String doctorSlug;
    private String status;
    private String dayOfWeek;
    private Long startTime;
    private Long endTime;
}
