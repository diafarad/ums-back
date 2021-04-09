package com.api.docsen.dao;

import com.api.docsen.model.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialiteRepository extends JpaRepository<Specialite, Integer> {
    public List<Specialite> findByService_Id(int id);
    public Specialite findById(int id);
}
