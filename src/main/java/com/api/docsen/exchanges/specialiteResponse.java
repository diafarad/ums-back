package com.api.docsen.exchanges;
import com.api.docsen.model.*;
import java.io.Serializable;

public class specialiteResponse implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
    private int id;
    private String libelle;
    private serviceRequest service;

    public specialiteResponse() {
    }


    public specialiteResponse(int id, String libelle, serviceRequest service) {
        this.id = id;
        this.libelle = libelle;
        this.service = service;
    }

    public serviceRequest getService() {
        return this.service;
    }

    public void setService(serviceRequest service) {
        this.service = service;
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

  




}
