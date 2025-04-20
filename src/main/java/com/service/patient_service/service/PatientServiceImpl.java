package com.service.patient_service.service;

import com.service.patient_service.dto.PatientRequest;
import com.service.patient_service.dto.PatientResponse;
import com.service.patient_service.entity.Patient;
import com.service.patient_service.exception.ApiException;
import com.service.patient_service.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    @Cacheable(value = "patients")
    public List<PatientResponse> getPatients() {
        return patientRepository.findAllByDeletedAt(0L).stream().map(
                data -> PatientResponse.builder()
                        .slug(data.getSlug())
                        .name(data.getName())
                        .email(data.getEmail())
                        .phoneNumber(data.getPhoneNumber())
                        .address(data.getAddress())
                        .gender(data.getGender())
                        .birthDate(data.getBirthDate())
                        .build()).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "patient", key = "#slug")
    public PatientResponse getPatientBySlug(String slug) {

        return patientRepository.findFirstBySlugAndDeletedAt(slug, 0L)
                .map(
                        data -> PatientResponse.builder()
                                .slug(data.getSlug())
                                .name(data.getName())
                                .email(data.getEmail())
                                .phoneNumber(data.getPhoneNumber())
                                .address(data.getAddress())
                                .gender(data.getGender())
                                .birthDate(data.getBirthDate())
                                .build())
                .orElseThrow(() -> new ApiException("Patient Not Found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    @CacheEvict(value = "patients", allEntries = true)
    public PatientResponse addPatient(PatientRequest patientRequest) {
        Patient patient = new Patient();
        patient.setSlug(slugify(patientRequest.getName()));
        patient.setName(patientRequest.getName());
        patient.setEmail(patientRequest.getEmail());
        patient.setPhoneNumber(patientRequest.getPhoneNumber());
        patient.setAddress(patientRequest.getAddress());
        patient.setGender(patientRequest.getGender());
        patient.setBirthDate(patientRequest.getBirthDate());
        patient.setCreatedAt(Instant.now().getEpochSecond());
        patient.setUpdatedAt(null);
        patient.setDeletedAt(0L);
        Patient savePatient = patientRepository.save(patient);

        return PatientResponse.builder()
                .slug(savePatient.getSlug())
                .name(savePatient.getName())
                .email(savePatient.getEmail())
                .phoneNumber(savePatient.getPhoneNumber())
                .address(savePatient.getAddress())
                .gender(savePatient.getGender())
                .birthDate(savePatient.getBirthDate())
                .build();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "patients", key = "'all'"),
            @CacheEvict(value = "patientBySlug", key = "#slug")
    })
    public PatientResponse updatePatient(PatientRequest patientRequest, String slug) {
        Patient findPatient = patientRepository.findFirstBySlugAndDeletedAt(slug, 0L)
                .orElseThrow(() -> new ApiException("Patient Not Found", HttpStatus.NOT_FOUND));

        findPatient.setSlug(slugify(patientRequest.getName()));
        findPatient.setName(patientRequest.getName());
        findPatient.setEmail(patientRequest.getEmail());
        findPatient.setPhoneNumber(patientRequest.getPhoneNumber());
        findPatient.setAddress(patientRequest.getAddress());
        findPatient.setGender(patientRequest.getGender());
        findPatient.setBirthDate(patientRequest.getBirthDate());
        findPatient.setUpdatedAt(Instant.now().getEpochSecond());
        Patient savePatient = patientRepository.save(findPatient);

        return PatientResponse.builder()
                .slug(savePatient.getSlug())
                .name(savePatient.getName())
                .email(savePatient.getEmail())
                .phoneNumber(savePatient.getPhoneNumber())
                .address(savePatient.getAddress())
                .gender(savePatient.getGender())
                .birthDate(savePatient.getBirthDate())
                .build();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "patients", key = "'all'"),
            @CacheEvict(value = "patientBySlug", key = "#slug")
    })
    public String deletePatient(String slug) {
        Patient findPatient = patientRepository.findFirstBySlugAndDeletedAt(slug, 0L)
                .orElseThrow(() -> new ApiException("Slug Not Found", HttpStatus.NOT_FOUND));

        findPatient.setDeletedAt(Instant.now().getEpochSecond());
        patientRepository.save(findPatient);
        return "Patient Deleted Successfully";
    }

    public static String slugify(String input) {
        String baseSlug = input
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");

        int randomNumber = new Random().nextInt(9000) + 1000; // random 4 digit antara 1000-9999
        return baseSlug + "-" + randomNumber;
    }
}
