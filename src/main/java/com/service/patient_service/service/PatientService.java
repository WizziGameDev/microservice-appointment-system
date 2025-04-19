package com.service.patient_service.service;

import com.service.patient_service.dto.PatientRequest;
import com.service.patient_service.dto.PatientResponse;

import java.util.List;

public interface PatientService {

    // Get All Patient Not Deleted
    public List<PatientResponse> getPatients();

    // Get Patient By slug
    public PatientResponse getPatientBySlug(String slug);

    // Add Patient
    public PatientResponse addPatient(PatientRequest patientRequest);

    // Update Patient
    public PatientResponse updatePatient(PatientRequest patientRequest, String slug);

    // Delete Patient
    public String deletePatient(String slug);
}
