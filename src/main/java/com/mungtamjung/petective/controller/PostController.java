package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.PostDTO;
import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("home")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO){
        try{
            PostEntity post = PostDTO.toEntity(postDTO);
            PostEntity registerPost = postService.create(post);
            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, registerPost);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/lost")
    public ResponseEntity<?> getLostPostList(){
        try{
            List<PostEntity> lostPostList = postService.retrieveLostPostList(writer);
            ResponseDTO responseDTO = new ResponseDTO(true, 200,null, lostPostList);
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> getFinePostList(){
        try{
            List<PostEntity> findPostList = postService.retrieveFindPostList();
            ResponseDTO responseDTO = new ResponseDTO(true, 200,null, findPostList);
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }



}
