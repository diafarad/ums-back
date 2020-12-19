package com.api.docsen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


//@EqualsAndHashCode(exclude = {"service", "specialites"})
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Medecin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min=2, max = 100)
    private String nom;

    @NotBlank
    @Size(min=2, max = 150)
    private String prenom;

    @Temporal(TemporalType.DATE)
    private Date datenaissance;

    @NotBlank
    @Size(max = 20)
    private String tel;

    @Column(length = 200)
    private String adresse;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hopital_id")
    private Hopital hopital;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "medecin", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private List<RendezVous> rendezVous;

    @OneToMany(mappedBy = "medecin", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private List<Note> notes;

    @ManyToOne
    @JoinColumn(name = "idSpecialite")
    @JsonManagedReference
    private Specialite specialite;
}
