package com.mungtamjung.petective.service;

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

    public List<InterestEntity> getInterestsByUser(String userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return interestRepository.findByUserId(user);
    }
}
