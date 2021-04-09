package com.api.docsen.exchanges;

import java.io.Serializable;
import java.util.Date;

public class RdvResponse implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private Long idMed;
    private Long idPat;
    private Long id;
    private String nompatient;
    private String prenompatient;
    private String nommedecin;
    private String prenommedecin;
    private String photoUser;
    private String photoMedecin;
    private String telMedecin;
    private String telPatient;
    private String hopital;
    private String adresse;
    private String adressePatient;
    private Date dateRdv;
    private String etat;
    //need default constructor for JSON Parsing

    public String getAdressePatient() {
        return this.adressePatient;
    }

    public void setAdressePatient(String adressePatient) {
        this.adressePatient = adressePatient;
    }


    public Long getIdMed() {
        return this.idMed;
    }

    public void setIdMed(Long idMed) {
        this.idMed = idMed;
    }

    public Long getIdPat() {
        return this.idPat;
    }

    public void setIdPat(Long idPat) {
        this.idPat = idPat;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTelPatient() {
        return this.telPatient;
    }

    public void setTelPatient(String telPatient) {
        this.telPatient = telPatient;
    }


    public String getNompatient() {
        return this.nompatient;
    }

    public void setNompatient(String nompatient) {
        this.nompatient = nompatient;
    }

    public String getPrenompatient() {
        return this.prenompatient;
    }

    public void setPrenompatient(String prenompatient) {
        this.prenompatient = prenompatient;
    }

    public String getNommedecin() {
        return this.nommedecin;
    }

    public void setNommedecin(String nommedecin) {
        this.nommedecin = nommedecin;
    }

    public String getPrenommedecin() {
        return this.prenommedecin;
    }

    public void setPrenommedecin(String prenommedecin) {
        this.prenommedecin = prenommedecin;
    }

    public RdvResponse( String nompatient, String prenompatient, String nommedecin, String prenommedecin, String photoUser, String photoMedecin, String adrresseMedecin, String hopital, String adresse, Date dateRdv, String etat) {
        
        this.nompatient = nompatient;
        this.prenompatient = prenompatient;
        this.nommedecin = nommedecin;
        this.prenommedecin = prenommedecin;
        this.photoUser = photoUser;
        this.photoMedecin = photoMedecin;
        this.telMedecin = adrresseMedecin;
        this.hopital = hopital;
        this.adresse = adresse;
        this.dateRdv = dateRdv;
        this.etat = etat;
    }



    public String getEtat() {
        return this.etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public RdvResponse()
    {
    }

    

    public String getPhotoMedecin() {
        return this.photoMedecin;
    }

    public void setPhotoMedecin(String photoMedecin) {
        this.photoMedecin = photoMedecin;
    }

    public String getTelMedecin() {
        return this.telMedecin;
    }

    public void setTelMedecin(String adrresseMedecin) {
        this.telMedecin = adrresseMedecin;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
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
