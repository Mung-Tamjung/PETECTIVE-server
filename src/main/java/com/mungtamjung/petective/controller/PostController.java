package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.PostDTO;
import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.InterestEntity;
import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.service.InterestService;
import com.mungtamjung.petective.service.PostService;
import com.mungtamjung.petective.service.S3UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("home")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private InterestService interestService;

    @Autowired
    private S3UploadService s3UploadService;

    @PostMapping(value = "/post", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createPost(@RequestPart(value="data") PostDTO postDTO, @RequestPart(name="file")List<MultipartFile> multipartFiles){
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
                    //.image(urls)
                    .build();

            PostEntity createdPost = postService.create(postEntity);

            //이미지 제외 먼저 저장 후 postid 받아와서 이미지 제목 설정
            List<String> urls = s3UploadService.saveFile(multipartFiles, 'O', createdPost.getId()); //O: post 이미지
            createdPost.setImage(urls);
            //엔티티 업데이트
            createdPost=postService.update(createdPost);

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

    // 관심글 등록
    @PostMapping("/lost/{postId}/interest")
    public ResponseEntity<?> addInterestLost(@PathVariable String postId, Authentication authentication) {
        return addInterest(postId, authentication);
    }

    @PostMapping("/find/{postId}/interest")
    public ResponseEntity<?> addFavoriteFind(@PathVariable String postId, Authentication authentication) {
        return addInterest(postId, authentication);
    }

    public ResponseEntity<?> addInterest(@PathVariable String postId, Authentication authentication) {
        try {
            String userId = authentication.getName();
            InterestEntity interest = interestService.addInterest(userId, postId);
            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, interest);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

}
