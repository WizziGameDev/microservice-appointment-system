package com.appointment.appointment_service.feign.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityResponse {

    private String dayOfWeek;

    private Long startTime;

    private Long endTime;
}
