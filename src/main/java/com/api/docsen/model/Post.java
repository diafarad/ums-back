package com.api.docsen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column
    private String title;

    @Lob
    @Basic
    private byte[] image;

    @Lob
    @Column
    @NotEmpty
    private String content;

    @Column
    private int likes;

    @Column
    @Temporal(TemporalType.DATE)
    private Date createdOn;

    @Column
    @Temporal(TemporalType.DATE)
    private Date updatedOn;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "idAdmin")
    @JsonManagedReference
    private Admin admin;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private List<PostComment> postComments;

}
