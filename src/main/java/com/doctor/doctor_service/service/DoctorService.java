package com.doctor.doctor_service.service;

import com.doctor.doctor_service.dto.DoctorRequest;
import com.doctor.doctor_service.dto.DoctorResponse;

import java.util.List;

public interface DoctorService {

    // Get all doctor and availability
    List<DoctorResponse> getDoctors();

    // Get doctor
    DoctorResponse getDoctorBySlug(String slug);

    // Add doctor
    DoctorResponse addDoctor(DoctorRequest doctorRequest);

    // Update doctor
    DoctorResponse updateDoctor(DoctorRequest doctorRequest, String slug);

    // Delete doctor
    String deleteDoctor(String slug);
}
