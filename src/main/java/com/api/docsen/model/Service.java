package com.api.docsen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"medecins", "specialites"})
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Service implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 30)
    private String libelle;

    @JsonBackReference
    @OneToMany(mappedBy = "service")
    private List<Specialite> specialites;

    @Override
    public String toString() {
        return libelle;
    }
}
