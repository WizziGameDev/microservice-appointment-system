package com.service.patient_service.repository;

import com.service.patient_service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findAllByDeletedAt(Long deletedAt);

    Optional<Patient> findFirstBySlugAndDeletedAt(String slug, Long deletedAt);
}
