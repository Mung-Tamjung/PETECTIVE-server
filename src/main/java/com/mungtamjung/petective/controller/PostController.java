package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.PostDTO;
import com.mungtamjung.petective.dto.PostDetailDTO;
import com.mungtamjung.petective.dto.PostSimpleDTO;
import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.InterestEntity;
import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("home")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private InterestService interestService;

    @Autowired
    private PostImageService postImageService;

    @Autowired
    private S3UploadService s3UploadService;

    // 글 작성
    @PostMapping(value = "/post", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createPost(@RequestPart(value="data") PostDTO postDTO, @RequestPart(name="file")List<MultipartFile> multipartFiles){
        try{
            // 현재 인증된 사용자 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String writer_id = authentication.getName(); // 사용자 이름 (username)을 가져옴
            //double[] encoding = postDTO.getEncoding();
            UserEntity writer = userService.getUserDetail(writer_id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            PostEntity postEntity = PostEntity.builder()
                    .postCategory(postDTO.getPostCategory())
                    .petCategory(postDTO.getPetCategory())
                    .title(postDTO.getTitle())
                    .content(postDTO.getContent())
                    //.writer(writer) // writer에 로그인된 사용자 정보 설정
                    .lostDate(postDTO.getLostDate())
                    .images(new ArrayList<>())
                    .breed(postDTO.getBreed())
                    .encoding(postDTO.getEncoding())
                    .build();
            postEntity.setWriter(writer);

            PostEntity createdPost = postService.create(postEntity, multipartFiles);
            createdPost.getWriter().setPassword(null); //password 보안 목적으로 null 설정 후 전송


            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, createdPost);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 실종 글 리스트 조회 (스크롤 방식)
    @GetMapping("/lost")
    public ResponseEntity<?> getLostPostList(@RequestParam int offset, @RequestParam int limit){
        try{
            Pageable pageable = PageRequest.of(offset, limit);
            Page<PostSimpleDTO> lostPostPage = postService.retrievePostList(0, pageable); // 카테고리 0 : 실종 글
            ResponseDTO responseDTO = new ResponseDTO(true, 200,null, lostPostPage.getContent());
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 발견 글 리스트 조회 (스크롤 방식)
    @GetMapping("/find")
    public ResponseEntity<?> getFindPostList(@RequestParam int offset, @RequestParam int limit){
        try{
            Pageable pageable = PageRequest.of(offset, limit);
            Page<PostSimpleDTO> findPostPage = postService.retrievePostList(1, pageable); // 카테고리 1 : 발견 글
            ResponseDTO responseDTO = new ResponseDTO(true, 200,null, findPostPage.getContent());
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 실종 글 상세 조회
    @GetMapping("/lost/{postId}")
    public ResponseEntity<?> getLostPostDetail(@PathVariable("postId") String postId){
        try{
            PostDetailDTO post = postService.retrievePost(postId);
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

    // 발견 글 상세 조회
    @GetMapping("/find/{postId}")
    public ResponseEntity<?> getFindPostDetail(@PathVariable("postId") String postId){
        try{
            PostDetailDTO post = postService.retrievePost(postId);

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
    public ResponseEntity<?> addInterestFind(@PathVariable String postId, Authentication authentication) {
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

    // 글 검색
    @GetMapping("/lost/search")
    public ResponseEntity<?> searchLostPosts(
            @RequestParam("keyword") String keyword,
            @RequestParam("petCategory") int petCategory,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit) {
        try {
            List<PostEntity> searchResults = postService.searchLostPosts(keyword, petCategory, offset, limit);
            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, searchResults);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/find/search")
    public ResponseEntity<?> searchFindPosts(
            @RequestParam("keyword") String keyword,
            @RequestParam("petCategory") int petCategory,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit) {
        try {
            List<PostEntity> searchResults = postService.searchFindPosts(keyword, petCategory, offset, limit);
            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, searchResults);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
