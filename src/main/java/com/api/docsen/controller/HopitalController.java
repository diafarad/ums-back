package com.api.docsen.controller;

import com.api.docsen.dao.*;
import com.api.docsen.exchanges.*;
import com.api.docsen.model.*;
import com.api.docsen.service.ISpecialiteService;
import com.api.docsen.service.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    private RdvRepository rdvRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/takerdv")
    public ResponseEntity<?> addRdv(@RequestBody RdvRequest rdvRequest) throws IOException {
        System.out.println("Date : " + rdvRequest.getDateRdv() + ", Id MED : " + rdvRequest.getIdMedecin());
        System.out.println("Id Pat : " + rdvRequest.getIdPatient());
        Medecin m = medecinRepository.getOne(rdvRequest.getIdMedecin());
        //System.out.println("Medecin : " +m.getPrenom()+ " " + m.getId());
        Patient p = patientRepository.getOne(rdvRequest.getIdPatient());
        //System.out.println("Patient : " +p.getPrenom()+ " " + p.getId());
        if(m != null && p != null && rdvRequest.getDateRdv() != null){
            System.out.println("Date OK");
            try {
                RendezVous rv = new RendezVous();
                rv.setDateRdv(rdvRequest.getDateRdv());
                rv.setMedecin(m);
                rv.setPatient(p);
                rv.setEtat("Demande en cours");
                rdvRepository.save(rv);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(new Response("ok", new RdvRequest(rdvRequest.getIdMedecin(),rdvRequest.getIdPatient(),rdvRequest.getDateRdv())));
        }
        else {
            System.out.println("Date NONE");
            return ResponseEntity.ok(new Response("error", new ErrorResponse("RDV_FAIL")));
        }
    }

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
