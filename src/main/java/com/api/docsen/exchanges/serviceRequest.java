package com.api.docsen.exchanges;
import java.io.Serializable;

public class serviceRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
    private int id;
    private String libelle;

    public serviceRequest() {
    }

    public serviceRequest(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
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
