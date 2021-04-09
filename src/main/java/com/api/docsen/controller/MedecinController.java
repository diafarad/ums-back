package com.api.docsen.controller;

import com.api.docsen.dao.*;
import com.api.docsen.exchanges.ErrorResponse;
import com.api.docsen.exchanges.HospitalDto;
import com.api.docsen.exchanges.MedecinAdminResponse;
import com.api.docsen.exchanges.MedecinResponse;
import com.api.docsen.exchanges.Response;
import com.api.docsen.exchanges.serviceRequest;
import com.api.docsen.exchanges.specialiteResponse;
import com.api.docsen.model.*;
import com.api.docsen.service.HopitalService;
import com.api.docsen.service.SpecialiteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/medecin")
@CrossOrigin
public class MedecinController {
    @Autowired
    private SpecialiteRepository specialiteRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HopitalRepository hopitalRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private HopitalService hop;

    @Autowired
    private SpecialiteService sp;
    @Autowired
    private RdvRepository rdv;


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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/hopital")
    public @ResponseBody boolean addHopital(@RequestBody HospitalDto hopitalDto){
        if(hopitalDto.getId()!=0){

         Hopital hopital =new Hopital();
         hopital.setId(hopitalDto.getId());
        hopital.setNom(hopitalDto.getNom());
        hopital.setAdresse(hopitalDto.getAdresse());
        hopital.setTel(hopitalDto.getTel());
        return hop.save(hopital);
        }else{
            Hopital hopital =new Hopital();
        hopital.setNom(hopitalDto.getNom());
        hopital.setAdresse(hopitalDto.getAdresse());
        hopital.setTel(hopitalDto.getTel());
        return hop.save(hopital);
        }
         

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/specialite")
    public @ResponseBody boolean addSpecialite(@RequestBody Specialite spe)
    {
        return sp.save(spe);
    }

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

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MEDECIN')")
    @GetMapping("/nombreRv")
    public @ResponseBody Long AllRV(){
        return rdv.count();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MEDECIN')")
    @GetMapping("/medecin/{id}")
    public @ResponseBody Optional<Medecin> getMedecin(@PathVariable Long id) {
        Optional<Medecin> medecin = medecinRepository.findById(id);
        return medecin;
    }




    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_MEDECIN') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public @ResponseBody List<MedecinResponse> findAllMedecins(){
        System.out.println("OKAY");
        List<MedecinResponse> list = new ArrayList<>();
        List<Medecin> listM = medecinRepository.findAll();
        for (int i = 0; i < listM.size(); i++) {
            MedecinResponse m = new MedecinResponse();
            m.setId(listM.get(i).getId());
            m.setPrenom(listM.get(i).getPrenom());
            m.setNom(listM.get(i).getNom());
            m.setTel(listM.get(i).getTel());
            m.setAdresse(listM.get(i).getHopital().getAdresse());
            // DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy ");
           // Date date = new Date(listM.get(i).getHopital().getAdresse());
         //   System.out.println("========="+dateFormat.format(date));
            m.setDateNaiss(listM.get(i).getDatenaissance());
            m.setHopital(listM.get(i).getHopital().getNom());
            m.setUsername(listM.get(i).getUser().getUsername());
            m.setPassword(listM.get(i).getUser().getPassword());
            m.setEmail(listM.get(i).getUser().getEmail());
            //String s = Base64.getEncoder().encodeToString(listM.get(i).getUser().getImage());
            //System.out.println("Photo : " + s);
            //byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            String decodedString;
            if (listM.get(i).getUser().getImage() != null){
                decodedString = Base64.getEncoder().encodeToString(listM.get(i).getUser().getImage());
                m.setPhoto(decodedString);
            }
            else
                decodedString = "ko";
            //System.out.println("Photo : " + decodedString);
            m.setSpecialite(listM.get(i).getSpecialite().getLibelle());
            m.setHopital(listM.get(i).getHopital().getNom());
            // m.setPhoto(listM.get(i).getUser().getImage().);
            list.add(m);
        }
        return list;
    }

    @PostMapping("/registerMedecin")
    public ResponseEntity<?> addUserMedecin(@RequestBody MedecinResponse medecinResponse) throws IOException {
        System.out.println("Username : " + medecinResponse.getUsername() + ", Email : " + medecinResponse.getEmail());
        System.out.println("BASE64 : " + medecinResponse.getImage());
        Base64.Decoder decoder = Base64.getDecoder();
        String encoded = medecinResponse.getImage();


        User u1 = userRepository.findByUsername(medecinResponse.getUsername());
        User u2 = userRepository.findByEmail(medecinResponse.getEmail());
        if(u1 != null){
            return ResponseEntity.ok(new Response("error", new ErrorResponse("USERNAME_EXIST")));
        }
        if(u2 != null){
            return ResponseEntity.ok(new Response("error", new ErrorResponse("MAIL_EXIST")));
        }
        try {
            Role r = roleRepository.findByName(RoleName.ROLE_MEDECIN);
            User u = new User();
            System.out.println("REG_AT : "+medecinResponse.getRegisterAt());
            Date regAt = new SimpleDateFormat("dd/MM/yyyy").parse(medecinResponse.getRegisterAt());
            u.setRegisterAt(regAt);
            u.setRole(r);
            u.setUsername(medecinResponse.getUsername());
            u.setEmail(medecinResponse.getEmail());
            u.setPassword(encoder.encode(medecinResponse.getPassword()));
            u.setImage(decoder.decode(encoded));
            //patientRequest.setPhoto("default.jpg");
            //System.out.println("Photo : "+patientRequest.getPhoto());
            // u.setData(patientRequest.getData());
            //u.setPhoto(patientRequest.getPhoto());
            Medecin m = new Medecin();
            m.setNom(medecinResponse.getNom());
            m.setPrenom(medecinResponse.getPrenom());
            m.setDatenaissance(medecinResponse.getDateNaiss());
            m.setAdresse(medecinResponse.getAdresse());
            m.setTel(medecinResponse.getTel());
            Hopital h = hopitalRepository.getOne(medecinResponse.getIdHopital());
            m.setHopital(h);
            Specialite spe = specialiteRepository.getOne(medecinResponse.getIdSpecialite());
            m.setSpecialite(spe);
            m.setUser(u);

            userRepository.save(u);
            medecinRepository.save(m);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new Response("ok", new MedecinResponse(medecinResponse.getNom(),medecinResponse.getPrenom(),medecinResponse.getEmail(),medecinResponse.getUsername())));
    }

    @PostMapping("/updateMedecin")
    public ResponseEntity<?> updateMedecin(@RequestBody MedecinAdminResponse medecinResponse) throws IOException {
        System.out.println("Username : " + medecinResponse.getUsername() + ", Email : " + medecinResponse.getEmail());
        System.out.println("BASE64 : " + medecinResponse.getImage());
        Base64.Decoder decoder = Base64.getDecoder();
        String encoded = medecinResponse.getImage();


       /*User u1 = userRepository.findByUsername(medecinResponse.getUsername());
        User u2 = userRepository.findByEmail(medecinResponse.getEmail());*/
       /* if(u1 != null){
            return ResponseEntity.ok(new Response("error", new ErrorResponse("USERNAME_EXIST")));
        }
        if(u2 != null){
            return ResponseEntity.ok(new Response("error", new ErrorResponse("MAIL_EXIST")));
        }*/
        try {
            Medecin med = medecinRepository.getOne(medecinResponse.getIdMedecin());
            User user = userRepository.getOne(med.getUser().getId());
           // Role r = roleRepository.findByName(RoleName.ROLE_MEDECIN);
            User u = new User();
            System.out.println("REG_AT : "+medecinResponse.getRegisterAt());
            //Date regAt = new SimpleDateFormat("dd/MM/yyyy").parse(medecinResponse.getRegisterAt());
            u.setId(medecinResponse.getIdUser());
            u.setRegisterAt(user.getRegisterAt());
            u.setLastLog(user.getLastLog());
            u.setRole(user.getRole());
            u.setUsername(medecinResponse.getUsername());
            u.setEmail(medecinResponse.getEmail());
            u.setPassword(user.getPassword());
            u.setImage(decoder.decode(encoded));
            //patientRequest.setPhoto("default.jpg");
            //System.out.println("Photo : "+patientRequest.getPhoto());
            // u.setData(patientRequest.getData());
            //u.setPhoto(patientRequest.getPhoto());
            Medecin m = new Medecin();
            m.setId(medecinResponse.getIdMedecin());
            m.setNom(medecinResponse.getNom());
            m.setPrenom(medecinResponse.getPrenom());
            m.setDatenaissance(medecinResponse.getDateNaiss());
            m.setAdresse(medecinResponse.getAdresse());
            m.setTel(medecinResponse.getTel());
            Hopital h = hopitalRepository.getOne(medecinResponse.getIdHopital());
            m.setHopital(h);
            Specialite spe = specialiteRepository.getOne(medecinResponse.getIdSpecialite());
            m.setSpecialite(spe);
            m.setUser(u);

            userRepository.save(u);
            medecinRepository.save(m);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new Response("ok", new MedecinResponse(medecinResponse.getNom(),medecinResponse.getPrenom(),medecinResponse.getEmail(),medecinResponse.getUsername())));
    }

}
