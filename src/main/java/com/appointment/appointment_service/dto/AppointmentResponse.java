package com.appointment.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private String slug;
    private String nameAppointment;
    private String dayOfWeek;
    private Long startTime;
    private Long endTime;
    private String status;
    private SimpleDoctorResponse doctor;
    private SimplePatientResponse patient;
}
