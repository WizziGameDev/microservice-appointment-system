package com.doctor.doctor_service.service;

import com.doctor.doctor_service.dto.AvailabilityResponse;
import com.doctor.doctor_service.dto.DoctorRequest;
import com.doctor.doctor_service.dto.DoctorResponse;
import com.doctor.doctor_service.entity.Availability;
import com.doctor.doctor_service.entity.Doctor;
import com.doctor.doctor_service.exception.ApiException;
import com.doctor.doctor_service.repository.DoctorRepository;
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
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    @Cacheable(value = "doctors")
    public List<DoctorResponse> getDoctors() {
        List<Doctor> doctors = doctorRepository.findAllByDeletedAt(0L);

        return doctors.stream().map(doctor -> DoctorResponse.builder()
                        .slug(doctor.getSlug())
                        .name(doctor.getName())
                        .email(doctor.getEmail())
                        .phoneNumber(doctor.getPhoneNumber())
                        .address(doctor.getAddress())
                        .gender(doctor.getGender())
                        .birthDate(doctor.getBirthDate())
                        .licenseNumber(doctor.getLicenseNumber())
                        .experienceYears(doctor.getExperienceYears())
                        .specialization(doctor.getSpecialization())
                        .availabilities(
                                doctor.getAvailabilities().stream()
                                        .map(av -> AvailabilityResponse.builder()
                                                .dayOfWeek(av.getDayOfWeek())
                                                .startTime(av.getStartTime())
                                                .endTime(av.getEndTime())
                                                .build())
                                        .collect(Collectors.toList())
                        )
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "doctors", key = "#slug")
    public DoctorResponse getDoctorBySlug(String slug) {

        Doctor doctor = doctorRepository.findFirstBySlugAndDeletedAt(slug, 0L)
                .orElseThrow(() -> new ApiException("Doctor Not Found", HttpStatus.NOT_FOUND));

        List<AvailabilityResponse> availabilityResponses = doctor.getAvailabilities().stream()
                .map(a -> AvailabilityResponse.builder()
                        .dayOfWeek(a.getDayOfWeek())
                        .startTime(a.getStartTime())
                        .endTime(a.getEndTime())
                        .build())
                .collect(Collectors.toList());

        return DoctorResponse.builder()
                .slug(doctor.getSlug())
                .name(doctor.getName())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .address(doctor.getAddress())
                .gender(doctor.getGender())
                .birthDate(doctor.getBirthDate())
                .licenseNumber(doctor.getLicenseNumber())
                .experienceYears(doctor.getExperienceYears())
                .specialization(doctor.getSpecialization())
                .availabilities(availabilityResponses)
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "doctors", allEntries = true)
    public DoctorResponse addDoctor(DoctorRequest doctorRequest) {
        Long now = Instant.now().toEpochMilli();

        Doctor doctor = Doctor.builder()
                .name(doctorRequest.getName())
                .email(doctorRequest.getEmail())
                .phoneNumber(doctorRequest.getPhoneNumber())
                .address(doctorRequest.getAddress())
                .gender(doctorRequest.getGender())
                .birthDate(doctorRequest.getBirthDate())
                .licenseNumber(doctorRequest.getLicenseNumber())
                .experienceYears(doctorRequest.getExperienceYears())
                .specialization(doctorRequest.getSpecialization())
                .slug(slugify(doctorRequest.getName()))
                .createdAt(now)
                .updatedAt(null)
                .deletedAt(0L)
                .build();

        List<Availability> availabilities = doctorRequest.getAvailabilities().stream()
                .map(req -> Availability.builder()
                        .dayOfWeek(req.getDayOfWeek())
                        .startTime(req.getStartTime())
                        .endTime(req.getEndTime())
                        .createdAt(now)
                        .updatedAt(null)
                        .deletedAt(0L)
                        .doctor(doctor)
                        .build())
                .collect(Collectors.toList());

        doctor.setAvailabilities(availabilities);

        Doctor savedDoctor = doctorRepository.save(doctor);

        // Mapping ke DoctorResponse
        List<AvailabilityResponse> availabilityResponses = savedDoctor.getAvailabilities().stream()
                .map(a -> AvailabilityResponse.builder()
                        .dayOfWeek(a.getDayOfWeek())
                        .startTime(a.getStartTime())
                        .endTime(a.getEndTime())
                        .build())
                .collect(Collectors.toList());

        return DoctorResponse.builder()
                .slug(savedDoctor.getSlug())
                .name(savedDoctor.getName())
                .email(savedDoctor.getEmail())
                .phoneNumber(savedDoctor.getPhoneNumber())
                .address(savedDoctor.getAddress())
                .gender(savedDoctor.getGender())
                .birthDate(savedDoctor.getBirthDate())
                .licenseNumber(savedDoctor.getLicenseNumber())
                .experienceYears(savedDoctor.getExperienceYears())
                .specialization(savedDoctor.getSpecialization())
                .availabilities(availabilityResponses)
                .build();
    }


    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "patients", key = "'all'"),
            @CacheEvict(value = "patientBySlug", key = "#slug")
    })
    public DoctorResponse updateDoctor(DoctorRequest doctorRequest, String slug) {
        Doctor resultDoctor = doctorRepository.findFirstBySlugAndDeletedAt(slug, 0L)
                .orElseThrow(() -> new ApiException("Doctor Not Found", HttpStatus.NOT_FOUND));

        Long now = Instant.now().toEpochMilli();

        resultDoctor.builder()
                .name(doctorRequest.getName())
                .email(doctorRequest.getEmail())
                .phoneNumber(doctorRequest.getPhoneNumber())
                .address(doctorRequest.getAddress())
                .gender(doctorRequest.getGender())
                .birthDate(doctorRequest.getBirthDate())
                .licenseNumber(doctorRequest.getLicenseNumber())
                .experienceYears(doctorRequest.getExperienceYears())
                .specialization(doctorRequest.getSpecialization())
                .slug(slugify(doctorRequest.getName()))
                .createdAt(now)
                .updatedAt(null)
                .deletedAt(0L)
                .build();

        List<Availability> availabilities = doctorRequest.getAvailabilities().stream()
                .map(req -> Availability.builder()
                        .dayOfWeek(req.getDayOfWeek())
                        .startTime(req.getStartTime())
                        .endTime(req.getEndTime())
                        .createdAt(now)
                        .updatedAt(null)
                        .deletedAt(0L)
                        .doctor(resultDoctor)
                        .build())
                .collect(Collectors.toList());

        resultDoctor.setAvailabilities(availabilities);

        Doctor updateDoctor = doctorRepository.save(resultDoctor);

        // Mapping ke DoctorResponse
        List<AvailabilityResponse> availabilityResponses = updateDoctor.getAvailabilities().stream()
                .map(a -> AvailabilityResponse.builder()
                        .dayOfWeek(a.getDayOfWeek())
                        .startTime(a.getStartTime())
                        .endTime(a.getEndTime())
                        .build())
                .collect(Collectors.toList());

        return DoctorResponse.builder()
                .slug(updateDoctor.getSlug())
                .name(updateDoctor.getName())
                .email(updateDoctor.getEmail())
                .phoneNumber(updateDoctor.getPhoneNumber())
                .address(updateDoctor.getAddress())
                .gender(updateDoctor.getGender())
                .birthDate(updateDoctor.getBirthDate())
                .licenseNumber(updateDoctor.getLicenseNumber())
                .experienceYears(updateDoctor.getExperienceYears())
                .specialization(updateDoctor.getSpecialization())
                .availabilities(availabilityResponses)
                .build();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "patients", key = "'all'"),
            @CacheEvict(value = "patientBySlug", key = "#slug")
    })
    public String deleteDoctor(String slug) {
        Doctor resultDoctor = doctorRepository.findFirstBySlugAndDeletedAt(slug, 0L)
                .orElseThrow(() -> new ApiException("Doctor Not Found", HttpStatus.NOT_FOUND));

        Long deletedAt = Instant.now().toEpochMilli();

        resultDoctor.setDeletedAt(deletedAt);

        if (resultDoctor.getAvailabilities() != null) {
            resultDoctor.getAvailabilities().forEach(availability -> availability.setDeletedAt(deletedAt));
        }

        doctorRepository.save(resultDoctor);

        return "Successfully deleted";
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
