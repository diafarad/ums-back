package com.api.docsen.controller;

import com.api.docsen.dao.PatientRepository;
import com.api.docsen.dao.RoleRepository;
import com.api.docsen.dao.UserRepository;
import com.api.docsen.model.*;
import com.api.docsen.exchanges.ErrorResponse;
import com.api.docsen.exchanges.PatientRequest;
import com.api.docsen.exchanges.Response;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.zip.Deflater;

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

    /*@PostMapping("/upload")
    public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        /*ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));
        imageRepository.save(img);*/
        //User u = new User();
        //u.setPhoto(file.getOriginalFilename());
        //u.setData(file.getBytes());
        //return ResponseEntity.status(HttpStatus.OK);
    //}

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    /*@PostMapping("/upload")
    public boolean getImage(@RequestParam("file") MultipartFile file) {

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
    }*/

    @PostMapping("/registerPatient")
    public ResponseEntity<?> addUserPatient(@RequestBody PatientRequest patientRequest) throws IOException {
        System.out.println("Username : " + patientRequest.getUsername() + ", Email : " + patientRequest.getEmail());
        System.out.println("BASE64 : " + patientRequest.getBlob());
        Base64.Decoder decoder = Base64.getDecoder();
        String encoded = patientRequest.getBlob();


        User u1 = userRepository.findByUsername(patientRequest.getUsername());
        User u2 = userRepository.findByEmail(patientRequest.getEmail());
        if(u1 != null){
            return ResponseEntity.ok(new Response("error", new ErrorResponse("USERNAME_EXIST")));
        }
        if(u2 != null){
            return ResponseEntity.ok(new Response("error", new ErrorResponse("MAIL_EXIST")));
        }
        try {
            Role r = roleRepository.findByName(RoleName.ROLE_USER);
            User u = new User();
            System.out.println("REG_AT : "+patientRequest.getRegisterAt());
            Date regAt = new SimpleDateFormat("dd/MM/yyyy").parse(patientRequest.getRegisterAt());
            u.setRegisterAt(regAt);
            u.setRole(r);
            u.setUsername(patientRequest.getUsername());
            u.setEmail(patientRequest.getEmail());
            u.setPassword(encoder.encode(patientRequest.getPassword()));
            u.setImage(decoder.decode(encoded));
            //patientRequest.setPhoto("default.jpg");
            //System.out.println("Photo : "+patientRequest.getPhoto());
           // u.setData(patientRequest.getData());
            //u.setPhoto(patientRequest.getPhoto());
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
