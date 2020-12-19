package com.api.docsen.dao;

import com.api.docsen.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RdvRepository extends JpaRepository<RendezVous, Long> {

}
