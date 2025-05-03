package com.appointment.appointment_service.controller;

import com.appointment.appointment_service.dto.AppointmentListResponse;
import com.appointment.appointment_service.dto.AppointmentRequest;
import com.appointment.appointment_service.dto.AppointmentResponse;
import com.appointment.appointment_service.dto.WebResponse;
import com.appointment.appointment_service.feign.response.DoctorResponse;
import com.appointment.appointment_service.service.AppointmentServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping(value = "/api/v1")
public class AppointmentController {

    @Autowired
    AppointmentServiceImpl appointmentServiceImpl;

    // Get List Off Appointment
    @SneakyThrows
    @GetMapping(value = "/appointments",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<AppointmentListResponse>> appointments(){
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<List<AppointmentListResponse>> future = executor.submit(() -> appointmentServiceImpl.getAppointments());

            return WebResponse.<List<AppointmentListResponse>>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    // Get Detail Appointment
    @SneakyThrows
    @GetMapping(value = "/appointment/{slug}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AppointmentResponse> getAppointmentBySlug(@PathVariable(value = "slug") String slug){
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<AppointmentResponse> future = executor.submit(() -> appointmentServiceImpl.getAppointmentBySlug(slug));

            return WebResponse.<AppointmentResponse>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    // Post Appointment
    @SneakyThrows
    @PostMapping(value = "/appointment",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AppointmentResponse> addAppointment(@RequestBody AppointmentRequest appointmentRequest){
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<AppointmentResponse> future = executor.submit(() -> appointmentServiceImpl.addAppointment(appointmentRequest));

            return WebResponse.<AppointmentResponse>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    // Update Appointment
    @SneakyThrows
    @PutMapping(value = "/appointment/{slug}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AppointmentResponse> updateAppointment(@RequestBody AppointmentRequest appointmentRequest, @PathVariable(value = "slug") String slug){
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<AppointmentResponse> future = executor.submit(() -> appointmentServiceImpl.updateAppointment(appointmentRequest, slug));

            return WebResponse.<AppointmentResponse>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    // Delete Appointment
    @SneakyThrows
    @DeleteMapping(value = "/appointment/{slug}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deleteAppointment(@PathVariable(value = "slug") String slug){
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> future = executor.submit(() -> appointmentServiceImpl.deleteAppointment(slug));

            return WebResponse.<String>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }

    // Update Status Appointment
    @SneakyThrows
    @PutMapping(value = "/appointment/{slug}/{status}")
    public WebResponse<String> updateStatus(@PathVariable(value = "slug") String slug, @PathVariable(value = "status") String status){
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> future = executor.submit(() -> appointmentServiceImpl.deleteAppointment(appointmentServiceImpl.updateStatusAppointment(slug, status)));

            return WebResponse.<String>builder()
                    .statusCode(200)
                    .data(future.get())
                    .errors(null)
                    .build();
        }
    }
}
