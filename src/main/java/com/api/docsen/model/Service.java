package com.api.docsen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"medecins", "specialites"})
@Entity

public class Service  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 30)
    private String libelle;

   // @JsonIgnoreProperties(value = "Service", allowSetters = true)

    @JsonBackReference
    @OneToMany(mappedBy = "service")
    private List<Specialite> specialites;


    public Service() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Specialite> getSpecialites() {
        return this.specialites;
    }

    public void setSpecialites(List<Specialite> specialites) {
        this.specialites = specialites;
    }

    public Service(int id, String libelle, List<Specialite> specialites) {
        this.id = id;
        this.libelle = libelle;
        this.specialites = specialites;
    }
   

    
}
