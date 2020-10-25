package com.api.docsen.controller;

import com.api.docsen.dao.PatientRepository;
import com.api.docsen.dao.RoleRepository;
import com.api.docsen.dao.UserRepository;
import com.api.docsen.model.*;
import com.api.docsen.exchanges.ErrorResponse;
import com.api.docsen.exchanges.PatientRequest;
import com.api.docsen.exchanges.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
public class RegisterController {

    private static String UPLOADED_FOLDER = "C://Users//diafara//Documents//imageDocSen//";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping(value = "upload/imgUser")
    public boolean getImage(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(file.getBytes().length);
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        try {
            Path downloadedFile = Paths.get(file.getOriginalFilename());
            Files.deleteIfExists(downloadedFile);

            Files.copy(file.getInputStream(), downloadedFile);

            return true;
        }
        catch (IOException e) {
            LoggerFactory.getLogger(this.getClass()).error("pictureupload", e);
            return false;
        }
    }

    @PostMapping("/registerPatient")
    public ResponseEntity<?> addUserPatient(@RequestBody PatientRequest patientRequest){
        System.out.println("Username : " + patientRequest.getUsername() + ", Email : " + patientRequest.getEmail());
        User u1 = userRepository.findByUsername(patientRequest.getUsername());
        User u2 = userRepository.findByEmail(patientRequest.getEmail());
        if(u1 != null){
            return ResponseEntity.ok(new Response("error", new ErrorResponse("USERNAME_EXIST")));
        }
        if(u2 != null){
            return ResponseEntity.ok(new Response("error", new ErrorResponse("MAIL_EXIST")));
        }
        try {
            /*byte[] bytes = {};
            Path path = null;
            if (!patientRequest.getFiles()[0].getName().equals("")) {
                MultipartFile file = patientRequest.getFiles()[0];
                bytes = file.getBytes();
                path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                patientRequest.setPhoto(file.getOriginalFilename());
            } else {
                patientRequest.setPhoto("default.jpg");
            }
            try {
                if (bytes.length != 0) {
                    Files.write(path, bytes);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }*/

            Role r = roleRepository.findByName(RoleName.ROLE_USER);
            User u = new User();
            u.setRole(r);
            u.setUsername(patientRequest.getUsername());
            u.setEmail(patientRequest.getEmail());
            u.setPassword(encoder.encode(patientRequest.getPassword()));
            patientRequest.setPhoto("default.jpg");
            u.setPhoto(patientRequest.getPhoto());
            Patient p = new Patient();
            p.setNom(patientRequest.getNom());
            p.setPrenom(patientRequest.getPrenom());
            p.setDatenaissance(patientRequest.getDateNaiss());
            p.setAdresse(patientRequest.getAdresse());
            p.setTel(patientRequest.getTel());
            p.setUser(u);

            userRepository.save(u);
            patientRepository.save(p);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new Response("ok", new PatientRequest(patientRequest.getNom(),patientRequest.getPrenom(),patientRequest.getEmail(),patientRequest.getUsername())));
    }
}
