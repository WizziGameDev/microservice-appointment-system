package com.appointment.appointment_service.feign;

import com.appointment.appointment_service.dto.WebResponse;
import com.appointment.appointment_service.feign.response.PatientResponse;
import lombok.SneakyThrows;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("PATIENT-SERVICE")
public interface PatientInterface {


    @SneakyThrows
    @GetMapping(value = "/api/v1/patients",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<PatientResponse>> getPatients();

    @SneakyThrows
    @GetMapping(value = "/api/v1/patient/{slug}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PatientResponse> getPatientBySlug(@PathVariable("slug") String slug);
}
