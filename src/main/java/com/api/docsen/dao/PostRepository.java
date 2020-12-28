package com.api.docsen.dao;

import com.api.docsen.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p order by p.id desc")
    public int getIdRoleAdmin();

    public Post findTopByOrderByIdDesc();
}
