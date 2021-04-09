package com.api.docsen.controller;

import com.api.docsen.config.JwtTokenUtil;
import com.api.docsen.dao.AdminRepository;
import com.api.docsen.dao.MedecinRepository;
import com.api.docsen.dao.PatientRepository;
import com.api.docsen.dao.UserRepository;
import com.api.docsen.model.Admin;
import com.api.docsen.model.Medecin;
import com.api.docsen.model.Patient;
import com.api.docsen.model.User;
import com.api.docsen.exchanges.*;
import com.api.docsen.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private MedecinRepository  medecinRepository;

    @Autowired
    PasswordEncoder encoder;


    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/roles", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public List<String> getUserRoles(@RequestParam("username") String username) throws Exception {
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        return userDetails.getAuthorities().stream()
                .map(u->((GrantedAuthority) u)
                        .getAuthority()).collect(Collectors.toList());
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest authenticationRequest) {
        System.out.println(authenticationRequest.getUsername()+" - "+authenticationRequest.getPassword());
        final UserDetails details = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        if(details != null) {
            Authentication authentication = null;
            try {
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = jwtTokenUtil.generateToken(details);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                System.out.println("PPPPPP"+jwt + "PPPPPP"+ userDetails.getUsername() +  "PPPPPP"+userDetails.getAuthorities() );
                
                ResponseJwt responseJwt = new ResponseJwt(jwt, userDetails.getUsername(), userDetails.getAuthorities());
                return ResponseEntity.ok(new Response("ok", responseJwt));
            } catch (DisabledException e) {
                return ResponseEntity.ok(new Response("error", new ErrorResponse("USER_DISABLED")));
            } catch (BadCredentialsException e) {
                return ResponseEntity.ok(new Response("error", new ErrorResponse("BAD_CREDENTIALS")));
            }
        }
        return ResponseEntity.ok(new Response("error", new ErrorResponse("INVALID_USERNAME")));
    }

    @PostMapping("/admin/adminEdite")
    @ResponseBody
    public ResponseEntity<?>editeProfil(@RequestBody AdminResponse adminResponse){
        User u = userRepository.getOne(adminResponse.getIdUser());

        if(encoder.matches(adminResponse.getPassWrd(), u.getPassword())){

            u.setId(adminResponse.getIdUser());
            u.setUsername(adminResponse.getUsername());
            u.setPassword(encoder.encode(adminResponse.getPassword()));
            userRepository.save(u);
            return ResponseEntity.ok(new Response("ok", adminResponse));
        }else
        {
            return ResponseEntity.ok(new Response("error", new ErrorResponse("Le mot de passe incorrect")));
        }
    }

    @GetMapping("/getUser/{userName}")
    @ResponseBody
    public ResponseEntity<?> getUtilisateur(@PathVariable(value = "userName") String username){
        PatientResponse patientResponse = null;
        System.out.println("Parameter " + username);
        User u = userRepository.findByUsername(username);
        if (u != null){
            System.out.println("Username : " + u.getUsername() + " Email : " + u.getEmail());
            Patient p = patientRepository.findPatientByUser_Username(u.getUsername());
            if (p != null){
                System.out.println("Patient : " + p.getPrenom() + " " + p.getNom());
                patientResponse = new PatientResponse();
                patientResponse.setId(p.getId());
                patientResponse.setPrenom(p.getPrenom());
                patientResponse.setNom(p.getNom());
                patientResponse.setDateNaiss(p.getDatenaissance());
                patientResponse.setAdresse(p.getAdresse());
                patientResponse.setTel(p.getTel());
                patientResponse.setGroupeSanguin(p.getGroupeSanguin());
                patientResponse.setEmail(u.getEmail());
                patientResponse.setUsername(u.getUsername());
                patientResponse.setPassword(u.getPassword());

                String decodedString;
                if (p.getUser().getImage() != null){
                    decodedString = Base64.getEncoder().encodeToString(p.getUser().getImage());
                    patientResponse.setPhoto(decodedString);
                }
                else
                    decodedString = "ko";
                //patientResponse.setPhoto(u.getPhoto());
                return ResponseEntity.ok(new Response("ok", patientResponse));
            }
            else{
                System.out.println("No result for Patient");
                return ResponseEntity.ok(new Response("error", new ErrorResponse("NO_RESULT")));
            }
        }
        return ResponseEntity.ok(new Response("error", new ErrorResponse("INVALID_USERNAME")));
    }

    @GetMapping("/getAdmin/{userName}")
    @ResponseBody
    public ResponseEntity<?> getAdministrateur(@PathVariable(value = "userName") String username){
        AdminResponse adminResponse = null;
        System.out.println("Parameter " + username);
        User u = userRepository.findByUsername(username);
        if (u != null){
            System.out.println("Username : " + u.getUsername() + " Email : " + u.getEmail());
            Admin a = adminRepository.getAdminByUserUsername(u.getUsername());
            if (a != null){
                System.out.println("Patient : " + a.getPrenom() + " " + a.getNom());
                adminResponse = new AdminResponse();
                adminResponse.setId(a.getId());
                adminResponse.setPrenom(a.getPrenom());
                adminResponse.setNom(a.getNom());
                adminResponse.setDateNaiss(a.getDatenaissance());
                adminResponse.setAdresse(a.getAdresse());
                adminResponse.setTel(a.getTel());
                adminResponse.setEmail(u.getEmail());
                adminResponse.setIdUser(u.getId());
                adminResponse.setUsername(u.getUsername());
                adminResponse.setPassword(u.getPassword());

                String decodedString;
                if (a.getUser().getImage() != null){
                    decodedString = Base64.getEncoder().encodeToString(a.getUser().getImage());
                    adminResponse.setPhoto(decodedString);
                }
                else
                    decodedString = "ko";
                //patientResponse.setPhoto(u.getPhoto());
                return ResponseEntity.ok(new Response("ok", adminResponse));
            }
            else{
                System.out.println("No result for Patient");
                return ResponseEntity.ok(new Response("error", new ErrorResponse("NO_RESULT")));
            }
        }
        return ResponseEntity.ok(new Response("error", new ErrorResponse("INVALID_USERNAME")));
    }

    @GetMapping("/getUtilisateur/{userName}")
    @ResponseBody
    public ResponseEntity<?> getUtilisateurByUsername(@PathVariable(value = "userName") String username){
        PatientResponse patientResponse = null;
        System.out.println("Parameter " + username);
        User u = userRepository.findByUsername(username);
        return ResponseEntity.ok(new Response("ok", u));
    }

    @GetMapping("/getMedecin/{userName}")
    @ResponseBody
    public ResponseEntity<?> getMedecinByUsername(@PathVariable(value = "userName") String username){
        MedecinAdminResponse med = null;
        System.out.println("Parameter " + username);
        User u = userRepository.findByUsername(username);
        Medecin m = medecinRepository.findMedecinByUser_Username(u.getUsername());
        if(m!=null){
            med=new MedecinAdminResponse();
            med.setNom(m.getNom());
            med.setPrenom(m.getPrenom());
            med.setUsername(u.getUsername());
            med.setTel(m.getTel());
            med.setEmail(u.getEmail());
            med.setIdMedecin(u.getId());
            med.setAdresse(m.getAdresse());
            med.setDateNaiss(m.getDatenaissance());
            String decodedString;
                if (m.getUser().getImage() != null){
                    decodedString = Base64.getEncoder().encodeToString(m.getUser().getImage());
                    med.setPhoto(decodedString);
                }
                else
                    decodedString = "ko";
                //patientResponse.setPhoto(u.getPhoto());
                
            return ResponseEntity.ok(new Response("ok", med));
        }
         return ResponseEntity.ok(new Response("error", new ErrorResponse("INVALID_USERNAME")));
    }
}
