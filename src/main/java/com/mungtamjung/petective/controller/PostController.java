package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.PostDTO;
import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("home")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO){
        try{
            // 현재 인증된 사용자 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String writer = authentication.getName(); // 사용자 이름 (username)을 가져옴

            PostEntity postEntity = PostEntity.builder()
                    .postCategory(postDTO.getPostCategory())
                    .petCategory(postDTO.getPetCategory())
                    .title(postDTO.getTitle())
                    .content(postDTO.getContent())
                    .writer(writer) // writer에 로그인된 사용자 정보 설정
                    .lostDate(postDTO.getLostDate())
                    .build();

            PostEntity createdPost = postService.create(postEntity);
            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, createdPost);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/lost")
    public ResponseEntity<?> getLostPostList(){
        try{
            List<PostEntity> lostPostList = postService.retrievePostList(0); // 카테고리 0 : 실종 글
            ResponseDTO responseDTO = new ResponseDTO(true, 200,null, lostPostList);
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> getFindPostList(){
        try{
            List<PostEntity> findPostList = postService.retrievePostList(1); // 카테고리 1 : 발견 글
            ResponseDTO responseDTO = new ResponseDTO(true, 200,null, findPostList);
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("lost/{postId}")
    public ResponseEntity<?> getLostPostDetail(@PathVariable("postId") String postId){
        try{
            Optional<?> post = postService.retrievePost(postId);
            if(post==null){
                throw new RuntimeException("Post doesn't exist");
            }
            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, post);
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("find/{postId}")
    public ResponseEntity<?> getFindPostDetail(@PathVariable("postId") String postId){
        try{
            Optional<?> post = postService.retrievePost(postId);
            if(post==null){
                throw new RuntimeException("Post doesn't exist");
            }
            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, post);
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


}
