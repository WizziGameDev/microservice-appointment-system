package com.appointment.appointment_service.service;

import com.appointment.appointment_service.dto.AppointmentListResponse;
import com.appointment.appointment_service.dto.AppointmentRequest;
import com.appointment.appointment_service.dto.AppointmentResponse;
import com.appointment.appointment_service.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    // Get All Appointment
    List<AppointmentListResponse> getAppointments();

    // Get Appointment
    AppointmentResponse getAppointmentBySlug(String slug);

    // Add Appointment
    AppointmentResponse addAppointment(AppointmentRequest appointmentRequest);

    // Update Appointment
    AppointmentResponse updateAppointment(AppointmentRequest appointmentRequest, String slug);

    // Delete Appointment
    String deleteAppointment(String slug);

    // Update Status Appointment
    String updateStatusAppointment(String slug, String status);
}
