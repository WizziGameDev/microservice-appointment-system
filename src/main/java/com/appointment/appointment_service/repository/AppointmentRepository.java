package com.appointment.appointment_service.repository;

import com.appointment.appointment_service.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    List<Appointment> findAllByDeletedAt(Long deletedAt);

    Optional<Appointment> findFirstBySlugAndDeletedAt(String slug, Long deletedAt);
}
