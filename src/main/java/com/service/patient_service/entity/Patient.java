package com.service.patient_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "patients")
public class Patient {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String slug;

    private String name;

    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    private String address;

    private String gender;

    @Column(name = "birth_date")
    private Long birthDate;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "deleted_at")
    private Long deletedAt;
}
