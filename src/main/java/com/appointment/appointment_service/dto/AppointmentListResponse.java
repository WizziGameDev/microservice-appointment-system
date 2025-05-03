package com.appointment.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentListResponse {

    private String slug;

    private String nameAppointment;

    private String dayOfWeek;

    private Long startTime;

    private Long endTime;

    private String status; // "PENDING", "CONFIRMED", "CANCELLED"
}
