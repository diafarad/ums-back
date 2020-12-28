package com.api.docsen.dao;

import com.api.docsen.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    public List<PostComment> getAllByPostId(Long id);
}
