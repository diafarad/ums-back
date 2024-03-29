package com.api.docsen.exchanges;

import java.io.Serializable;
import java.util.Date;

public class PatientRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

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
    private String blob;
    private String registerAt;

    //need default constructor for JSON Parsing
    public PatientRequest()
    {
    }

    public PatientRequest(String prenom, String nom, String tel, String adresse, Date dateNaiss, String groupeSanguin, String login, String password, String email, String photo, String data, String registerAt) {
        this.prenom = prenom;
        this.nom = nom;
        this.tel = tel;
        this.adresse = adresse;
        this.dateNaiss = dateNaiss;
        this.groupeSanguin = groupeSanguin;
        this.username = login;
        this.password = password;
        this.email = email;
        this.photo = photo;
        this.blob = data;
        this.registerAt = registerAt;
    }

    public PatientRequest(String nom, String prenom, String email, String username)
    {
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(Date dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getGroupeSanguin() {
        return groupeSanguin;
    }

    public void setGroupeSanguin(String groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBlob() {
        return blob;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }

    public String getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(String registerAt) {
        this.registerAt = registerAt;
    }
}
