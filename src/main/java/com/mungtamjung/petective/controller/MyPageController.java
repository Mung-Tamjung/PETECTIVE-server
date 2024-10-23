package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.PostSimpleDTO;
import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.InterestEntity;
import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.service.InterestService;
import com.mungtamjung.petective.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("mypage")
public class MyPageController {

    @Autowired
    private PostService postService;

    @Autowired
    private InterestService interestService;

    @GetMapping("/mypost")
    public ResponseEntity<?> getUserPostList(@RequestParam String writer) {
        try {
            List<PostEntity> userPostList = postService.getPostsByWriter(writer);
            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, userPostList);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 관심글 조회
    @GetMapping("/interest")
    public ResponseEntity<?> getInterest(Authentication authentication) {
        try {
            String userId = authentication.getName();
            List<PostSimpleDTO> interestPosts = interestService.getInterestPostsByUser(userId);
            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, interestPosts);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

}
