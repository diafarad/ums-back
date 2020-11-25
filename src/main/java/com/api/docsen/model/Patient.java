package com.api.docsen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements Serializable {
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

    @Column(length = 5)
    private String groupeSanguin;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private List<RendezVous> rendezVous;
}
