package com.api.docsen.service;

import com.api.docsen.dao.SpecialiteRepository;
import com.api.docsen.model.Specialite;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class SpecialiteService implements ISpecialiteService {
    @Autowired
    private SpecialiteRepository specialiteRepository;
    @Override
    public Specialite findById(int id) {
        return specialiteRepository.getOne(id);
    }
}
