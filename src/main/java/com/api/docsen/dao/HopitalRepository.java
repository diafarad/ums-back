package com.api.docsen.dao;

import com.api.docsen.model.Hopital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HopitalRepository extends JpaRepository<Hopital, Long> {

}
