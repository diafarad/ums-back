package com.api.docsen.dao;

import com.api.docsen.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /*@Query(value = "SELECT p FROM Patient p JOIN p.user u WHERE u.username=:name")
    public Patient getPatientByUserUsername(@Param("name")String uname);*/

    public Patient findPatientByUser_Username(String uname);

}
