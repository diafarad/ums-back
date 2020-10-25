package com.api.docsen.controller;

import com.api.docsen.dao.MedecinRepository;
import com.api.docsen.exchanges.ErrorResponse;
import com.api.docsen.model.Medecin;
import com.api.docsen.service.IServiceService;
import com.api.docsen.service.ISpecialiteService;
import com.api.docsen.service.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/medecin")
@CrossOrigin
public class MedecinController {
    @Autowired
    private MedecinService medecinService;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private IServiceService serviceService;

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

    @PreAuthorize("hasAuthority('ROLE_SECRETAIRE') or hasAuthority('ROLE_MEDECIN') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") long id)
    {
        try {
            Optional<Medecin> med = medecinRepository.findById(id);
            if(med.isPresent()){
                medecinRepository.delete(med.get());
                ResponseEntity.ok(new ErrorResponse("succes"));
            }
            ResponseEntity.ok(new ErrorResponse("echec"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PreAuthorize("hasAuthority('ROLE_MEDECIN') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public @ResponseBody
    List<Medecin> findAll(){
        return medecinService.findAll();
    }
}
