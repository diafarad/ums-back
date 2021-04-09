package com.api.docsen.dao;

import com.api.docsen.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RdvRepository extends JpaRepository<RendezVous, Long> {
    public List<RendezVous> findAllByOrderByIdDesc();
    public List<RendezVous> findByEtat(String etat);
}
