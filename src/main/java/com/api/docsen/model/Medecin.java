package com.api.docsen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@EqualsAndHashCode(exclude = {"service", "specialites"})
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hopital_id", referencedColumnName = "id")
    private Hopital hopital;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "medecin_specialite",
            joinColumns = @JoinColumn(name = "medecin_id"),
            inverseJoinColumns = @JoinColumn(name = "specialite_id"))
    private List<Specialite> specialites;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "medecin", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private List<RendezVous> rendezVous;

    @OneToMany(mappedBy = "medecin", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private List<Note> notes;
}
