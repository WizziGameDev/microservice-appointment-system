package com.appointment.appointment_service.feign;

import com.appointment.appointment_service.dto.WebResponse;
import com.appointment.appointment_service.feign.response.DoctorResponse;
import lombok.SneakyThrows;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("DOCTOR-SERVICE")
public interface DoctorInterface {

    @SneakyThrows
    @GetMapping(value = "/api/v1/doctors",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<DoctorResponse>> getDoctors();

    // Get Doctor By Slug
    @SneakyThrows
    @GetMapping(value = "api/v1/doctor/{slug}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<DoctorResponse> getDoctorBySlug(@PathVariable(value = "slug") String slug);
}
