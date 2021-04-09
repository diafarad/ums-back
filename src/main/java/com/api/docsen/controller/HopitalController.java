package com.api.docsen.controller;

import com.api.docsen.dao.*;
import com.api.docsen.exchanges.*;
import com.api.docsen.model.*;
import com.api.docsen.service.ISpecialiteService;
import com.api.docsen.service.MedecinService;
import com.api.docsen.service.ServiceService;
import com.api.docsen.service.SpecialiteService;

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
    private SpecialiteService specialiteService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private RdvRepository rdvRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ServiceRepository serviceRepo;
    @Autowired
    private ServiceService serv;

    @PreAuthorize("hasAuthority('ROLE_MEDECIN') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/service/all")
    public @ResponseBody List<Service> findAllService(){
        return serviceRepo.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value="/service/add",method = RequestMethod.POST, consumes="application/json")
    public @ResponseBody boolean addHopital(@RequestBody serviceRequest service){
        if(service.getId()!=0){
        Service servic = new Service();
        servic.setId(service.getId());
        servic.setLibelle(service.getLibelle());
       //servic.setSpecialites(null);
        return serv.save(servic);
        }else{
            Service servic = new Service();
        servic.setLibelle(service.getLibelle());
       //servic.setSpecialites(null);
        return serv.save(servic);
        }
        

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/service/{id}")
    public @ResponseBody Service getService(@PathVariable int id){
       Service service = serviceRepo.findById(id);
        return service;
    }

    @PostMapping("/takerdv")
    public ResponseEntity<?> addRdv(@RequestBody RdvRequest rdvRequest) throws IOException {
        Medecin m = medecinRepository.getOne(rdvRequest.getIdMedecin());
        //System.out.println("Medecin : " +m.getPrenom()+ " " + m.getId());
        Patient p = patientRepository.getOne(rdvRequest.getIdPatient());
        //System.out.println("Patient : " +p.getPrenom()+ " " + p.getId());
        if(m != null && p != null && rdvRequest.getDateRdv() != null && rdvRequest.getId()!=null){
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


    @PostMapping("/validerdv")
    public ResponseEntity<?> validerRdv(@RequestBody RdvResponse rdvRequest) throws IOException {
        Medecin m = medecinRepository.getOne(rdvRequest.getIdMed());
        //System.out.println("Medecin : " +m.getPrenom()+ " " + m.getId());
        Patient p = patientRepository.getOne(rdvRequest.getIdPat());
        //System.out.println("Patient : " +p.getPrenom()+ " " + p.getId());
        if(m != null && p != null && rdvRequest.getDateRdv() != null && rdvRequest.getId()!=null&& rdvRequest.getId()!=null){
            System.out.println("Date OK");
            try {
                RendezVous rv = new RendezVous();
                rv.setId(rdvRequest.getId());
                rv.setDateRdv(rdvRequest.getDateRdv());
                rv.setMedecin(m);
                rv.setPatient(p);
                rv.setEtat("Demande valider");
                rdvRepository.save(rv);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(new Response("ok", new RdvRequest(rdvRequest.getIdMed(),rdvRequest.getIdPat(),rdvRequest.getDateRdv())));
        }
        else {
            System.out.println("Date NONE");
            return ResponseEntity.ok(new Response("error", new ErrorResponse("RDV_FAIL")));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/allRdv")
    public @ResponseBody List<RdvResponse> findAllRdv(){
        List<RdvResponse> list = new ArrayList<>();
        List<RendezVous> listM = rdvRepository.findAllByOrderByIdDesc();
        for (int i = 0; i < listM.size(); i++) {
            RdvResponse r = new RdvResponse();
            r.setId(listM.get(i).getId());
            r.setDateRdv(listM.get(i).getDateRdv());
            r.setNommedecin(listM.get(i).getMedecin().getNom());
            r.setPrenommedecin(listM.get(i).getMedecin().getPrenom());
            r.setHopital(listM.get(i).getMedecin().getHopital().getNom());
            r.setAdresse(listM.get(i).getMedecin().getHopital().getAdresse());
            r.setNompatient(listM.get(i).getPatient().getPrenom() );
            r.setPrenompatient( listM.get(i).getPatient().getNom());
            r.setTelMedecin(listM.get(i).getMedecin().getTel());
            r.setEtat(listM.get(i).getEtat());
            String decodedString;
            if (listM.get(i).getPatient().getUser().getImage() != null){
                decodedString = Base64.getEncoder().encodeToString(listM.get(i).getPatient().getUser().getImage());
                r.setPhotoUser(decodedString);
            }
            if (listM.get(i).getMedecin().getUser().getImage() != null){
                decodedString = Base64.getEncoder().encodeToString(listM.get(i).getMedecin().getUser().getImage());
                r.setPhotoMedecin(decodedString);
            }
            list.add(r);
        }
        return list;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getDetailRdv/{id}")
    @ResponseBody
    public ResponseEntity<?> DetailsRdv(@PathVariable(value = "id") Long id) {
        RendezVous rdv = rdvRepository.getOne(id);
        if (rdv != null){
            RdvResponse rdvResponse = new RdvResponse();
            rdvResponse.setId(rdv.getId());
            rdvResponse.setIdMed(rdv.getMedecin().getId());
            rdvResponse.setIdPat(rdv.getPatient().getId());
            rdvResponse.setAdresse(rdv.getMedecin().getHopital().getAdresse());
            rdvResponse.setNompatient(rdv.getPatient().getPrenom() );
            rdvResponse.setPrenompatient( rdv.getPatient().getNom());
            rdvResponse.setHopital(rdv.getMedecin().getHopital().getNom());
            rdvResponse.setNommedecin(rdv.getMedecin().getNom());
            rdvResponse.setEtat(rdv.getEtat());
            rdvResponse.setTelMedecin(rdv.getMedecin().getTel());
            rdvResponse.setPrenommedecin(rdv.getMedecin().getPrenom());
            rdvResponse.setDateRdv(rdv.getDateRdv());
            rdvResponse.setTelPatient(rdv.getPatient().getTel());
          rdvResponse.setAdressePatient(rdv.getPatient().getAdresse());
         
            String decodedString;
            if (rdv.getPatient().getUser().getImage() != null){
                decodedString = Base64.getEncoder().encodeToString(rdv.getPatient().getUser().getImage());
                rdvResponse.setPhotoUser(decodedString);
            }

            if (rdv.getMedecin().getUser().getImage() != null){
                decodedString = Base64.getEncoder().encodeToString(rdv.getMedecin().getUser().getImage());
                rdvResponse.setPhotoMedecin(decodedString);
            }
            return ResponseEntity.ok(new Response("ok", rdvResponse));
        }
        else{
            System.out.println("No result for RDV");
            return ResponseEntity.ok(new Response("error", new ErrorResponse("INVALID_ID_RDV")));
        }
    }

    @GetMapping("/getRdv/{id}")
    @ResponseBody
    public ResponseEntity<?> showDetailsRdv(@PathVariable(value = "id") Long id) {
        RendezVous rdv = rdvRepository.getOne(id);
        if (rdv != null){
            RdvResponse rdvResponse = new RdvResponse();
            rdvResponse.setId(rdv.getId());
       
            rdvResponse.setAdresse(rdv.getMedecin().getHopital().getAdresse());
            rdvResponse.setNompatient(rdv.getPatient().getPrenom() + rdv.getPatient().getNom() );
           
            rdvResponse.setHopital(rdv.getMedecin().getHopital().getNom());
            rdvResponse.setNommedecin(rdv.getMedecin().getNom() + rdv.getMedecin().getPrenom());
            
            rdvResponse.setDateRdv(rdv.getDateRdv());
           
          
            String decodedString;
            if (rdv.getPatient().getUser().getImage() != null){
                decodedString = Base64.getEncoder().encodeToString(rdv.getPatient().getUser().getImage());
                rdvResponse.setPhotoUser(decodedString);
            }

          
            return ResponseEntity.ok(new Response("ok", rdvResponse));
        }
        else{
            System.out.println("No result for RDV");
            return ResponseEntity.ok(new Response("error", new ErrorResponse("INVALID_ID_RDV")));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_MEDECIN') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public @ResponseBody List<Hopital> findAllHopitaux(){
        return hopitalRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getOneH/{id}")
    public @ResponseBody HospitalDto oneHopi(@PathVariable Long id){
        Hopital ho = hopitalRepository.getOne(id);
        HospitalDto h = new HospitalDto() ;
        h.setId(ho.getId());
        h.setNom(ho.getNom());
        h.setAdresse(ho.getAdresse());
        h.setTel(ho.getTel());
        return h;
    }

    @PreAuthorize("hasAuthority('ROLE_MEDECIN') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/specialite/all")
    public @ResponseBody List<Specialite> findAllSpecialites(){
        return specialiteRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/specialite/add")
    public @ResponseBody boolean addSpecialite(@RequestBody specialiteResponse specialiteRes){

        if(specialiteRes.getId()!=0){
            Specialite specialite = new Specialite();
            Service serv = new Service();
            serv.setId(specialiteRes.getService().getId());
            serv.setLibelle(specialiteRes.getService().getLibelle());
            specialite.setLibelle(specialiteRes.getLibelle());
            specialite.setId(specialiteRes.getId());
            specialite.setService(serv);
            return specialiteService.save(specialite);
        }else{
            Specialite specialite = new Specialite();
            Service serv = new Service();
            serv.setId(specialiteRes.getService().getId());
            serv.setLibelle(specialiteRes.getService().getLibelle());
            specialite.setLibelle(specialiteRes.getLibelle());
            specialite.setService(serv);
            return specialiteService.save(specialite);
        }

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/specialite/{id}")
    public @ResponseBody Specialite getSpe(@PathVariable int id){
       Specialite special = specialiteRepository.findById(id);
        return special;
    }
}
