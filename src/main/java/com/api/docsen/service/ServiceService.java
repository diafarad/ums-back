package com.api.docsen.service;

import com.api.docsen.dao.ServiceRepository;
import com.api.docsen.model.Service;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class ServiceService  {
    @Autowired
    private ServiceRepository serviceRepository;
  
    public Service findById(int id) {
        return serviceRepository.getOne(id);
    }


    public boolean save(Service serv){
        if(serv!=null){
            serviceRepository.save(serv);
            return true;
        }else{return false;}
        
    }
}
