package com.api.docsen.exchanges;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PatientResponse implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private Long id;
    private String prenom;
    private String nom;
    private String tel;
    private String adresse;
    private Date dateNaiss;
    private String groupeSanguin;
    private String username;
    private String password;
    private String email;
    private String photo;
    private MultipartFile[] files;

    //need default constructor for JSON Parsing
    public PatientResponse()
    {
    }
}
