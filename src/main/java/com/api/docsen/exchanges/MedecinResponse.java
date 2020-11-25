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
public class MedecinResponse implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private Long id;
    private String prenom;
    private String nom;
    private String tel;
    private String adresse;
    private Date dateNaiss;
    private long idHopital;
    private String hopital;
    private String username;
    private String password;
    private String email;
    private String photo;
    private int idSpecialite;
    private String specialite;
    private String image;
    private String registerAt;
    //private MultipartFile[] files;

    //need default constructor for JSON Parsing
    public MedecinResponse()
    {
    }

    public MedecinResponse(String nom, String prenom, String email, String username)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.username = username;
    }
}
