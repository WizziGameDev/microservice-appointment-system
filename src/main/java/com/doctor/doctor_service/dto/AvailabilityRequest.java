package com.doctor.doctor_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityRequest {

    @NotBlank(message = "Day of week is required")
    @Pattern(
            regexp = "Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday",
            message = "Day of week must be a valid day (e.g., Monday, Tuesday, etc.)"
    )
    private String dayOfWeek;

    @NotNull(message = "Start time is required")
    @Positive(message = "Start time must be a positive timestamp")
    private Long startTime;

    @NotNull(message = "End time is required")
    @Positive(message = "End time must be a positive timestamp")
    private Long endTime;
}
