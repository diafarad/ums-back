package com.api.docsen.exchanges;

import java.util.Date;

public class PostCommentRequest {
    private String content;
    private Long iduser;
    private Long idpost;
    private Date datecom;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public Long getIdpost() {
        return idpost;
    }

    public void setIdpost(Long idpost) {
        this.idpost = idpost;
    }

    public Date getDatecom() {
        return datecom;
    }

    public void setDatecom(Date datecom) {
        this.datecom = datecom;
    }
}
