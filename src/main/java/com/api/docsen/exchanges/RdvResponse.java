package com.api.docsen.exchanges;

import java.io.Serializable;
import java.util.Date;

public class RdvResponse implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private Long id;
    private String patient;
    private String medecin;
    private String photoUser;
    private String hopital;
    private String adresse;
    private Date dateRdv;

    //need default constructor for JSON Parsing
    public RdvResponse()
    {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RdvResponse(String patient, String medecin, String photoUser, String hopital, String adresse, Date dateRdv) {
        this.patient = patient;
        this.medecin = medecin;
        this.photoUser = photoUser;
        this.hopital = hopital;
        this.adresse = adresse;
        this.dateRdv = dateRdv;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getMedecin() {
        return medecin;
    }

    public void setMedecin(String medecin) {
        this.medecin = medecin;
    }

    public String getPhotoUser() {
        return photoUser;
    }

    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }

    public String getHopital() {
        return hopital;
    }

    public void setHopital(String hopital) {
        this.hopital = hopital;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(Date dateRdv) {
        this.dateRdv = dateRdv;
    }
}
