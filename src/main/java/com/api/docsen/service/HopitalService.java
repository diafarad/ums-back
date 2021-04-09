package com.api.docsen.service;

import com.api.docsen.dao.HopitalRepository;
import com.api.docsen.model.Hopital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HopitalService {
    @Autowired
    private HopitalRepository hopitalRepository;
    
    public boolean save(Hopital hopital){
        if(hopital!=null){
            hopitalRepository.save(hopital);
            return true;
        }else
            {
                return false;
            }

    }
}
