package com.api.docsen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date dateRdv;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "idMedecin")
    @JsonManagedReference
    private Medecin medecin;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "idPatient")
    @JsonManagedReference
    private Patient patient;
}
