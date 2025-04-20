package com.doctor.doctor_service.controller;

import com.doctor.doctor_service.dto.DoctorRequest;
import com.doctor.doctor_service.dto.DoctorResponse;
import com.doctor.doctor_service.dto.WebResponse;
import com.doctor.doctor_service.service.DoctorService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping(value = "/api/v1")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // Get All Data Doctor
    @SneakyThrows
    @GetMapping(value = "/doctors",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<DoctorResponse>> getDoctors() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<List<DoctorResponse>> future = executor.submit(() -> doctorService.getDoctors());

            return WebResponse.<List<DoctorResponse>>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    // Get Doctor By Slug
    @SneakyThrows
    @GetMapping(value = "/doctor/{slug}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<DoctorResponse> getDoctors(@PathVariable(value = "slug") String slug) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<DoctorResponse> future = executor.submit(() -> doctorService.getDoctorBySlug(slug));

            return WebResponse.<DoctorResponse>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    // Add Doctor
    @SneakyThrows
    @PostMapping(value = "/doctor",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<DoctorResponse> addDoctor(@RequestBody DoctorRequest doctorRequest) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<DoctorResponse> future = executor.submit(() -> doctorService.addDoctor(doctorRequest));

            return WebResponse.<DoctorResponse>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    // Update Doctor
    @SneakyThrows
    @PutMapping(value = "/doctor/{slug}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<DoctorResponse> updateDoctor(@RequestBody DoctorRequest doctorRequest,
                                                    @PathVariable(value = "slug") String slug) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<DoctorResponse> future = executor.submit(() -> doctorService.updateDoctor(doctorRequest, slug));

            return WebResponse.<DoctorResponse>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    // Delete Doctor
    @SneakyThrows
    @DeleteMapping(value = "/doctor/{slug}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deleteDoctor(@PathVariable(value = "slug") String slug) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> future = executor.submit(() -> doctorService.deleteDoctor(slug));

            return WebResponse.<String>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }
}
