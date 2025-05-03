package com.appointment.appointment_service.service;

import com.appointment.appointment_service.dto.*;
import com.appointment.appointment_service.entity.Appointment;
import com.appointment.appointment_service.exception.ApiException;
import com.appointment.appointment_service.feign.DoctorInterface;
import com.appointment.appointment_service.feign.PatientInterface;
import com.appointment.appointment_service.feign.response.DoctorResponse;
import com.appointment.appointment_service.feign.response.PatientResponse;
import com.appointment.appointment_service.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    DoctorInterface doctorInterface;

    @Autowired
    PatientInterface patientInterface;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    @Cacheable(value = "appointments")
    public List<AppointmentListResponse> getAppointments() {

        List<Appointment> appointments = appointmentRepository.findAllByDeletedAt(0L);

        return appointments.stream()
                .map(appointment -> AppointmentListResponse.builder()
                        .slug(appointment.getSlug())
                        .nameAppointment(appointment.getNameAppointment())
                        .dayOfWeek(appointment.getDayOfWeek())
                        .startTime(appointment.getStartTime())
                        .endTime(appointment.getEndTime())
                        .status(appointment.getStatus())
                        .build())
                .toList();
    }

    @Override
    @Cacheable(value = "appointment", key = "#slug")
    public AppointmentResponse getAppointmentBySlug(String slug) {
        // Cari appointment
        Appointment appointment = appointmentRepository.findFirstBySlugAndDeletedAt(slug, 0L)
                .orElseThrow(() -> new ApiException("Appointment Not Found", HttpStatus.NOT_FOUND));

        // Ambil data doctor dan patient
        DoctorResponse doctorResponse = doctorInterface.getDoctorBySlug(appointment.getDoctorSlug()).getData();
        PatientResponse patientResponse = patientInterface.getPatientBySlug(appointment.getPatientSlug()).getData();

        // Validasi respons doctor dan patient
        if (doctorResponse == null) {
            throw new ApiException("Doctor Not Found", HttpStatus.NOT_FOUND);
        }

        if (patientResponse == null) {
            throw new ApiException("Patient Not Found", HttpStatus.NOT_FOUND);
        }

        // Bangun response ringan
        SimpleDoctorResponse simpleDoctor = SimpleDoctorResponse.builder()
                .slug(doctorResponse.getSlug())
                .name(doctorResponse.getName())
                .specialization(doctorResponse.getSpecialization())
                .build();

        SimplePatientResponse simplePatient = SimplePatientResponse.builder()
                .slug(patientResponse.getSlug())
                .name(patientResponse.getName())
                .build();

        // Bangun appointment response
        return AppointmentResponse.builder()
                .slug(appointment.getSlug())
                .nameAppointment(appointment.getNameAppointment())
                .dayOfWeek(appointment.getDayOfWeek())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .status(appointment.getStatus())
                .doctor(simpleDoctor)
                .patient(simplePatient)
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "appointment", allEntries = true)
    public AppointmentResponse addAppointment(AppointmentRequest request) {
        // Validasi doctor dan patient slug
        DoctorResponse doctor = doctorInterface.getDoctorBySlug(request.getDoctorSlug()).getData();
        if (doctor == null) {
            throw new ApiException("Doctor not found", HttpStatus.NOT_FOUND);
        }

        PatientResponse patient = patientInterface.getPatientBySlug(request.getPatientSlug()).getData();
        if (patient == null) {
            throw new ApiException("Patient not found", HttpStatus.NOT_FOUND);
        }

        Long now = System.currentTimeMillis();

        // Simpan ke database
        Appointment appointment = Appointment.builder()
                .slug(slugify(request.getNameAppointment()))
                .nameAppointment(request.getNameAppointment())
                .patientSlug(request.getPatientSlug())
                .doctorSlug(request.getDoctorSlug())
                .dayOfWeek(request.getDayOfWeek())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(request.getStatus())
                .createdAt(now)
                .updatedAt(0L)
                .deletedAt(0L)
                .build();

        appointmentRepository.save(appointment);

        // Bangun response
        return AppointmentResponse.builder()
                .slug(appointment.getSlug())
                .nameAppointment(appointment.getNameAppointment())
                .dayOfWeek(appointment.getDayOfWeek())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .status(appointment.getStatus())
                .doctor(SimpleDoctorResponse.builder()
                        .slug(doctor.getSlug())
                        .name(doctor.getName())
                        .specialization(doctor.getSpecialization())
                        .build())
                .patient(SimplePatientResponse.builder()
                        .slug(patient.getSlug())
                        .name(patient.getName())
                        .build())
                .build();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "appointments", key = "'all'"),
            @CacheEvict(value = "appointment", key = "#slug")
    })
    public AppointmentResponse updateAppointment(AppointmentRequest appointmentRequest, String slug) {
        // Ambil data appointment
        Appointment appointment = appointmentRepository.findFirstBySlugAndDeletedAt(slug, 0L)
                .orElseThrow(() -> new ApiException("Appointment Not Found", HttpStatus.NOT_FOUND));

        // Ambil dan validasi doctor
        DoctorResponse doctor = doctorInterface.getDoctorBySlug(appointmentRequest.getDoctorSlug()).getData();
        if (doctor == null) {
            throw new ApiException("Doctor not found", HttpStatus.BAD_REQUEST);
        }

        // Ambil dan validasi patient
        PatientResponse patient = patientInterface.getPatientBySlug(appointmentRequest.getPatientSlug()).getData();
        if (patient == null) {
            throw new ApiException("Patient not found", HttpStatus.BAD_REQUEST);
        }

        // Update data appointment
        appointment.setSlug(slugify(appointmentRequest.getNameAppointment()));
        appointment.setNameAppointment(appointmentRequest.getNameAppointment());
        appointment.setDoctorSlug(appointmentRequest.getDoctorSlug());
        appointment.setPatientSlug(appointmentRequest.getPatientSlug());
        appointment.setDayOfWeek(appointmentRequest.getDayOfWeek());
        appointment.setStartTime(appointmentRequest.getStartTime());
        appointment.setEndTime(appointmentRequest.getEndTime());
        appointment.setUpdatedAt(System.currentTimeMillis());

        // Simpan perubahan
        appointmentRepository.save(appointment);

        // Kembalikan response
        return AppointmentResponse.builder()
                .slug(appointment.getSlug())
                .nameAppointment(appointment.getNameAppointment())
                .dayOfWeek(appointment.getDayOfWeek())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .status(appointment.getStatus())
                .doctor(SimpleDoctorResponse.builder()
                        .slug(doctor.getSlug())
                        .name(doctor.getName())
                        .specialization(doctor.getSpecialization())
                        .build())
                .patient(SimplePatientResponse.builder()
                        .slug(patient.getSlug())
                        .name(patient.getName())
                        .build())
                .build();
    }


    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "appointments", key = "'all'"),
            @CacheEvict(value = "appointment", key = "#slug")
    })
    public String deleteAppointment(String slug) {
        Appointment appointment = appointmentRepository.findFirstBySlugAndDeletedAt(slug, 0L)
                .orElseThrow(() -> new ApiException("Appointment Not Found", HttpStatus.NOT_FOUND));

        appointment.setDeletedAt(System.currentTimeMillis());
        appointmentRepository.save(appointment);

        return "Appointment with slug '" + slug + "' successfully deleted.";
    }

    // Update Status Appointment
    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "appointments", key = "'all'"),
            @CacheEvict(value = "appointment", key = "#slug")
    })
    public String updateStatusAppointment(String slug, String status) {
        Appointment appointment = appointmentRepository.findFirstBySlugAndDeletedAt(slug, 0L)
                .orElseThrow(() -> new ApiException("Appointment Not Found", HttpStatus.NOT_FOUND));

        appointment.setStatus(status);
        appointmentRepository.save(appointment);

        return "Appointment with slug '" + slug + "' successfully updated status.";
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
