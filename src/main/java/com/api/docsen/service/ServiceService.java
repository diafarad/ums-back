package com.api.docsen.service;

import com.api.docsen.dao.ServiceRepository;
import com.api.docsen.model.Service;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {
    @Autowired
    private ServiceRepository serviceRepository;
    @Override
    public Service findById(int id) {
        return serviceRepository.getOne(id);
    }
}
