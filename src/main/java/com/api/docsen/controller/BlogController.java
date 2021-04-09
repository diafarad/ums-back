package com.api.docsen.controller;

import com.api.docsen.exchanges.ErrorResponse;
import com.api.docsen.model.Blog;
import com.api.docsen.model.Medecin;
import com.api.docsen.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/blog")
@CrossOrigin
public class BlogController {

    @Autowired
    private BlogService blogService;


    @PreAuthorize(" hasAuthority('ROLE_MEDECIN') or hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public @ResponseBody Boolean add(@RequestBody Blog blog)
    {
       return blogService.save(blog);
    }


    @GetMapping ("/add")
    public @ResponseBody
    List<Blog> all()
    {
        return blogService.all();
    }
}
