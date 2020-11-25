package com.api.docsen.controller;

import com.api.docsen.dao.*;
import com.api.docsen.exchanges.ErrorResponse;
import com.api.docsen.exchanges.MedecinResponse;
import com.api.docsen.model.Hopital;
import com.api.docsen.model.Medecin;
import com.api.docsen.model.Specialite;
import com.api.docsen.model.User;
import com.api.docsen.service.ISpecialiteService;
import com.api.docsen.service.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/hopital")
@CrossOrigin
public class HopitalController {

    @Autowired
    private HopitalRepository hopitalRepository;

    @Autowired
    private SpecialiteRepository specialiteRepository;

    @Autowired
    private ISpecialiteService specialiteService;


    /*@PreAuthorize("hasAuthority('ROLE_SECRETAIRE') or hasAuthority('ROLE_MEDECIN')")
    @PostMapping("/add")
    public @ResponseBody
    Medecin add(@RequestBody Medecin medecin){
        if(medecin.getService() != null){
            Service s = serviceService.findById(medecin.getService().getId());
            medecin.setService(s);
        }
        if(medecin.getSpecialites() != null){
            List<Specialite> sps = new ArrayList<>();
            List<Specialite> parc = medecin.getSpecialites();
            for (Specialite ss : parc) {
                sps.add(specialiteService.findById(ss.getId()));
            }
            if(!sps.isEmpty())
               medecin.setSpecialites(sps);
            else{
                medecin.setSpecialites(null);
            }
        }
        return medecinService.save(medecin);
    }*/

    @PreAuthorize("hasAuthority('ROLE_MEDECIN') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public @ResponseBody List<Hopital> findAllHopitaux(){
        return hopitalRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_MEDECIN') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/specialite/all")
    public @ResponseBody List<Specialite> findAllSpecialites(){
        return specialiteRepository.findAll();
    }
}
