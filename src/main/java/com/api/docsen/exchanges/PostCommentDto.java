package com.api.docsen.exchanges;

import java.util.Date;

public class PostCommentDto {
    private Long id;
    private String content;
    private String auteur;
    private String imgauteur;
    private Date dateCom;
    private int likes;

    public String getImgauteur() {
        return imgauteur;
    }

    public void setImgauteur(String imgauteur) {
        this.imgauteur = imgauteur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Date getDateCom() {
        return dateCom;
    }

    public void setDateCom(Date dateCom) {
        this.dateCom = dateCom;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
