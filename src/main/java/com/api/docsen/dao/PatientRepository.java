package com.api.docsen.dao;

import com.api.docsen.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    public Patient findByUserUsernameOrUserEmail(String username, String email);
}
