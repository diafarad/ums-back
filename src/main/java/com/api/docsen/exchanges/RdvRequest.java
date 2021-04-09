package com.api.docsen.exchanges;

import java.io.Serializable;
import java.util.Date;

public class RdvRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private Long id;
    private Date dateRdv;
    private Long idPatient;
    private Long idMedecin;


    //need default constructor for JSON Parsing
    public RdvRequest()
    {
    }

    public RdvRequest(Long idPatient, Long idMedecin, Date date) {
        this.idMedecin = idMedecin;
        this.idPatient = idPatient;
        this.dateRdv = date;
    }

    public Date getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(Date dateRdv) {
        this.dateRdv = dateRdv;
    }

    public Long getIdPatient() {
        return idPatient;

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setIdPatient(Long idPatient) {
        this.idPatient = idPatient;
    }

    public Long getIdMedecin() {
        return idMedecin;
    }

    public void setIdMedecin(Long idMedecin) {
        this.idMedecin = idMedecin;
    }
}
