package com.api.docsen.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column
    private String content;

    @Column
    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date dateCom;

    @Column
    private int likes;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "idPost")
    @JsonManagedReference
    private Post post;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonManagedReference
    private User user;

}
