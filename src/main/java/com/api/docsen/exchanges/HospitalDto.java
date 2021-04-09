package com.api.docsen.exchanges;

import java.io.Serializable;

public class HospitalDto implements Serializable {

    private long id;

    private String nom;

    private String tel;

    private String adresse;


    public HospitalDto() {
    }

    public HospitalDto(long id, String nom, String tel, String adresse) {
        this.id = id;
        this.nom = nom;
        this.tel = tel;
        this.adresse = adresse;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

}
