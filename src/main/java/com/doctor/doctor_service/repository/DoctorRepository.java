    package com.doctor.doctor_service.repository;

    import com.doctor.doctor_service.entity.Doctor;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;
    import java.util.Optional;

    @Repository
    public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

        List<Doctor> findAllByDeletedAt(Long deletedAt);

        Optional<Doctor> findFirstBySlugAndDeletedAt(String slug, Long deletedAt);
    }
