package com.service.patient_service.controller;

import com.service.patient_service.dto.PatientRequest;
import com.service.patient_service.dto.PatientResponse;
import com.service.patient_service.dto.WebResponse;
import com.service.patient_service.service.PatientServiceImpl;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping(value = "/api/v1")
public class PatientController {

    @Autowired
    private PatientServiceImpl patientServiceImpl;

    @SneakyThrows
    @GetMapping(value = "/patients",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<PatientResponse>> getPatients() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<List<PatientResponse>> future = executor.submit(() -> patientServiceImpl.getPatients());

            return WebResponse.<List<PatientResponse>>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    @SneakyThrows
    @GetMapping(value = "/patient/{slug}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PatientResponse> getPatient(@PathVariable("slug") String slug) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<PatientResponse> future = executor.submit(() -> patientServiceImpl.getPatientBySlug(slug));

            return WebResponse.<PatientResponse>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    @SneakyThrows
    @PostMapping(value = "/patient",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PatientResponse> addPatient(@Valid @RequestBody PatientRequest patientRequest) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<PatientResponse> future = executor.submit(() -> patientServiceImpl.addPatient(patientRequest));

            return WebResponse.<PatientResponse>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    @SneakyThrows
    @PutMapping(value = "/patient/{slug}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PatientResponse> updatePatient(@Valid @RequestBody PatientRequest patientRequest, @PathVariable("slug") String slug) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<PatientResponse> future = executor.submit(() -> patientServiceImpl.updatePatient(patientRequest, slug));

            return WebResponse.<PatientResponse>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    @SneakyThrows
    @DeleteMapping(value = "/patient/{slug}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deletePatient(@PathVariable("slug") String slug) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> future = executor.submit(() -> patientServiceImpl.deletePatient(slug));

            return WebResponse.<String>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }
}
