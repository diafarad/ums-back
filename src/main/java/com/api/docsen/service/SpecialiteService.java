package com.api.docsen.service;

import com.api.docsen.dao.SpecialiteRepository;
import com.api.docsen.model.Specialite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialiteService implements ISpecialiteService {
    @Autowired
    private SpecialiteRepository specialiteRepository;
    @Override
    public Specialite findById(int id) {
        return specialiteRepository.getOne(id);
    }

    public boolean save(Specialite sp){
        if(sp!=null){
            specialiteRepository.save(sp);
            return true;
        }else{return false;}
        
    }
}
