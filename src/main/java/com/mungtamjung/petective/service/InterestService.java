package com.mungtamjung.petective.service;

import com.mungtamjung.petective.dto.PostSimpleDTO;
import com.mungtamjung.petective.model.InterestEntity;
import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.repository.InterestRepository;
import com.mungtamjung.petective.repository.PostRepository;
import com.mungtamjung.petective.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class InterestService {
    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public InterestEntity addInterest(String userId, String postId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (interestRepository.existsByUserIdAndPostId(user, post)) {
            throw new RuntimeException("Already added to favorites");
        }

        InterestEntity interest = InterestEntity.builder()
                .userId(user)
                .postId(post)
                .build();
        return interestRepository.save(interest);
    }

    public List<PostSimpleDTO> getInterestPostsByUser(String userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<InterestEntity> interests = interestRepository.findByUserId(user);
        return interests.stream()
                .map(interest -> PostSimpleDTO.builder()
                        .id(interest.getPostId().getId())
                        .writer_name(interest.getPostId().getWriter().getUsername())
                        .postCategory(interest.getPostId().getPostCategory())
                        .title(interest.getPostId().getTitle())
                        .createdAt(interest.getPostId().getCreatedAt())
                        .lostDate(interest.getPostId().getLostDate())
                        .image(interest.getPostId().getImages().isEmpty() ? null : interest.getPostId().getImages().get(0).getUrl())
                        .build())
                .collect(Collectors.toList());
    }
}
