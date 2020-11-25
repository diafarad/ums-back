package com.api.docsen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"service", "medecins"})
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Specialite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String libelle;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @OneToMany(mappedBy = "specialite")
    @JsonBackReference
    private List<Medecin> medecins;

}
