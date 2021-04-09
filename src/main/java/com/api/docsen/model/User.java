package com.api.docsen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Blob;
import java.util.Date;
import java.util.List;


@Entity

@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String username;

    @NaturalId(mutable=true)
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min=6, max = 100)
    private String password;

    @Lob
    @Basic
    private byte[] image;

    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date registerAt;

    @Temporal(TemporalType.DATE)
    private Date lastLog;

    @Transient
    private MultipartFile files;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private List<PostComment> postComments;

    public Long getId() {
            return id;
    }

    public void setId(Long id) {
            this.id = id;
    }

    public String getUsername() {
            return username;
    }

    public void setUsername(String username) {
            this.username = username;
    }

    public String getEmail() {
            return email;
    }

    public void setEmail(String email) {
            this.email = email;
    }

    public String getPassword() {
            return password;
    }

    public void setPassword(String password) {
            this.password = password;
    }

    public byte[] getImage() {
            return image;
    }

    public void setImage(byte[] image) {
            this.image = image;
    }

    public Date getRegisterAt() {
            return registerAt;
    }

    public void setRegisterAt(Date registerAt) {
            this.registerAt = registerAt;
    }

    public Date getLastLog() {
            return lastLog;
    }

    public void setLastLog(Date lastLog) {
            this.lastLog = lastLog;
    }

    public MultipartFile getFiles() {
            return files;
    }

    public void setFiles(MultipartFile files) {
            this.files = files;
    }

    public Role getRole() {
            return role;
    }

    public void setRole(Role role) {
            this.role = role;
    }

    public List<PostComment> getPostComments() {
            return postComments;
    }

    public void setPostComments(List<PostComment> postComments) {
            this.postComments = postComments;
    }

    public User(Long id, @NotBlank @Size(min = 3, max = 50) String username,
                    @NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(min = 6, max = 100) String password,
                    byte[] image, @NotBlank Date registerAt, Date lastLog, MultipartFile files, Role role,
                    List<PostComment> postComments) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.password = password;
            this.image = image;
            this.registerAt = registerAt;
            this.lastLog = lastLog;
            this.files = files;
            this.role = role;
            this.postComments = postComments;
    }

    public User() {
    }
    



}