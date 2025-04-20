package com.service.patient_service.service;

import com.service.patient_service.dto.PatientRequest;
import com.service.patient_service.dto.PatientResponse;

import java.util.List;

public interface PatientService {

    // Get All Patient Not Deleted
    List<PatientResponse> getPatients();

    // Get Patient By slug
    PatientResponse getPatientBySlug(String slug);

    // Add Patient
    PatientResponse addPatient(PatientRequest patientRequest);

    // Update Patient
    PatientResponse updatePatient(PatientRequest patientRequest, String slug);

    // Delete Patient
    String deletePatient(String slug);
}
