package com.api.docsen.service;

import com.api.docsen.dao.BlogRepository;
import com.api.docsen.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository BlogRepository;

    public Boolean save(Blog blog){
        if(blog==null){
            return false;
        }else{
            BlogRepository.save(blog);
            return true;
        }

    }


    public List<Blog> all(){
        return BlogRepository.findAll();
    }


}
