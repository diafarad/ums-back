package com.api.docsen.service;

import com.api.docsen.dao.MedecinRepository;
import com.api.docsen.model.Medecin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService implements IMedecinService {
    @Autowired
    private MedecinRepository medecinRepository;
    @Override
    public Medecin save(Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    @Override
    public List<Medecin> findAll() {
        return medecinRepository.findAll();
    }

    public Medecin getMede(Long id){
        return medecinRepository.getOne(id);
    }
}
