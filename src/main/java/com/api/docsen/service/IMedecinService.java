package com.api.docsen.service;

import com.api.docsen.model.Medecin;

import java.util.List;

public interface IMedecinService {
    public Medecin save(Medecin medecin);
    public List<Medecin> findAll();
}
