package com.api.docsen.controller;

import com.api.docsen.dao.AdminRepository;
import com.api.docsen.dao.PostCommentRepository;
import com.api.docsen.dao.PostRepository;
import com.api.docsen.dao.UserRepository;
import com.api.docsen.exchanges.PostCommentDto;
import com.api.docsen.exchanges.PostCommentRequest;
import com.api.docsen.exchanges.PostDto;
import com.api.docsen.exchanges.Response;
import com.api.docsen.model.Admin;
import com.api.docsen.model.Post;
import com.api.docsen.model.PostComment;
import com.api.docsen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/post")
@CrossOrigin
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    AdminRepository adminRepository;

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MEDECIN')")
    @GetMapping("/post/{id}")
    public @ResponseBody PostDto getPost(@PathVariable Long id) {
        Post post = postRepository.getOne(id);
        PostDto po = new PostDto();
        po.setId(post.getId());
        po.setContent(post.getContent());
        po.setTitle(post.getTitle());
          String decodedString;
           if(post.getImage()!=null){
            decodedString = Base64.getEncoder().encodeToString(post.getImage());
            po.setImage(decodedString);
           }else{
            po.setImage("null");
           }
        
        return po;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        if(postDto.getId()!=0){

            Base64.Decoder decoder = Base64.getDecoder();
        String encoded = postDto.getImage();
        Admin admin = adminRepository.getAdminByUserUsername(postDto.getUsername());
        System.out.println("ADMIN : "+admin.getPrenom());

        Post p = new Post();
        p.setId(postDto.getId());
        p.setTitle(postDto.getTitle());
        p.setContent(postDto.getContent());
        p.setImage(decoder.decode(encoded));
        p.setCreatedOn(postDto.getDateCreate());
        p.setUpdatedOn(new Date());
        p.setAdmin(admin);
        Post p1 = postRepository.save(p);
        return ResponseEntity.ok(new Response("ok", new PostDto()));
        }else{
            Base64.Decoder decoder = Base64.getDecoder();
        String encoded = postDto.getImage();
        Admin admin = adminRepository.getAdminByUserUsername(postDto.getUsername());
        System.out.println("ADMIN : "+admin.getPrenom());

        Post p = new Post();
        p.setTitle(postDto.getTitle());
        p.setContent(postDto.getContent());
        p.setImage(decoder.decode(encoded));
        p.setCreatedOn(new Date());
        p.setAdmin(admin);
        Post p1 = postRepository.save(p);
        return ResponseEntity.ok(new Response("ok", new PostDto()));
        }
    }

    @PostMapping("/addComment")
    public ResponseEntity<?> addCommentPost(@RequestBody PostCommentRequest pComm) {
        System.out.println("ID Post : "+ pComm.getIdpost());
        System.out.println("ID User : "+ pComm.getIduser());
        Post post = postRepository.getOne(pComm.getIdpost());
        User user = userRepository.getOne(pComm.getIduser());
        PostComment p = new PostComment();
        p.setContent(pComm.getContent());
        p.setDateCom(new Date());
        p.setPost(post);
        p.setUser(user);
        PostComment p1 = postCommentRepository.save(p);
        return ResponseEntity.ok(new Response("ok", new PostCommentRequest()));
    }

    @PostMapping("/updateLike")
    public ResponseEntity<?> updateLikePost(@RequestBody PostDto postDetails) throws ResourceNotFoundException {
        System.out.println("UPDATE ENTER");
        Post post = postRepository.getOne(postDetails.getId());
        Admin admin = adminRepository.getAdminByUserUsername(postDetails.getUsername());
        if (post != null){
            post.setId(postDetails.getId());
            post.setLikes(postDetails.getLikes());
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            post.setCreatedOn(postDetails.getDateCreate());
            if (postDetails.getImage() != null ||postDetails.getImage() != ""){
                Base64.Decoder decoder = Base64.getDecoder();
                String encoded = postDetails.getImage();
                post.setImage(decoder.decode(encoded));
            }
            else {
                post.setImage(null);
            }
            post.setAdmin(admin);
            System.out.println("Prenom : " + post.getAdmin().getPrenom());
            postRepository.save(post);
        }
        else {
            System.out.println("LIKES NULL");
        }
        return ResponseEntity.ok(new Response("ok", new PostDto()));
    }

   


    @GetMapping("/all")
    public @ResponseBody List<PostDto> showAllPosts() {
        List<PostDto> list = new ArrayList<>();
        List<Post> listM = postRepository.findAll();
        for (int i = 0; i < listM.size(); i++) {
            PostDto p = new PostDto();
            int like=0;
            System.out.println(listM.get(i).getId());
          for(int j=0; j<listM.get(i).getPostComments().size(); j++){
                like=like+listM.get(i).getPostComments().get(j).getLikes();
          }
          System.out.println(like);
            p.setId(listM.get(i).getId());
            p.setTitle(listM.get(i).getTitle());
            p.setContent(listM.get(i).getContent());
            p.setDateCreate(listM.get(i).getCreatedOn());
            p.setLikes(like);
            String decodedString;
            if (listM.get(i).getImage() != null){
                decodedString = Base64.getEncoder().encodeToString(listM.get(i).getImage());
                p.setImage(decodedString);
            }
            else
                decodedString = "ko";
            //System.out.println("USERNAME : "+ listM.get(i).getAdmin().getUser().getUsername());
            p.setUsername(listM.get(i).getAdmin().getUser().getUsername());
            list.add(p);
        }
        return list;
    }

    @GetMapping("/get/{id}")
    public @ResponseBody PostDto getSinglePost(@PathVariable @RequestBody Long id) {
        Post p =  postRepository.getOne(id);
        PostDto postDto = new PostDto();
        postDto.setContent(p.getContent());
        postDto.setTitle(p.getTitle());
        postDto.setId(p.getId());
        return postDto;
    }

    @GetMapping("/allComments/{id}")
    public @ResponseBody List<PostCommentDto> showAllCommentPosts(@PathVariable @RequestBody Long id) {
        List<PostCommentDto> list = new ArrayList<>();
        List<PostComment> listCom = postCommentRepository.getAllByPostId(id);
        for (int j = 0; j < listCom.size(); j++) {
            PostCommentDto pCom = new PostCommentDto();
            pCom.setContent(listCom.get(j).getContent());
            pCom.setId(listCom.get(j).getId());
            pCom.setLikes(listCom.get(j).getLikes());
            pCom.setDateCom(listCom.get(j).getDateCom());
            String decodedString;
            if (listCom.get(j).getUser().getImage() != null){
                decodedString = Base64.getEncoder().encodeToString(listCom.get(j).getUser().getImage());
                pCom.setImgauteur(decodedString);
            }
            else
                decodedString = "ko";
            pCom.setAuteur("@"+listCom.get(j).getUser().getUsername());
            list.add(pCom);
        }
        return list;
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MEDECIN')")
    @GetMapping("/getLastPost")
    @ResponseBody
    public ResponseEntity<?> getLastPost() {
        Post p = postRepository.findTopByOrderByIdDesc();
        PostDto postDto = new PostDto();
        postDto.setId(p.getId());
        postDto.setLikes(p.getLikes());
        postDto.setTitle(p.getTitle());
        postDto.setContent(p.getContent());
        postDto.setUsername(p.getAdmin().getUser().getUsername());
        postDto.setDateCreate(p.getCreatedOn());
        String decodedString;
        if (p.getImage() != null){
            decodedString = Base64.getEncoder().encodeToString(p.getImage());
            postDto.setImage(decodedString);
        }
        postDto.setContent(p.getContent());
        return ResponseEntity.ok(new Response("ok", postDto));
    }
}
