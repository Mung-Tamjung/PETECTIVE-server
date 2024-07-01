package com.mungtamjung.petective.service;

import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public PostEntity create(final PostEntity postEntity) {

        if(postEntity ==null || postEntity.getTitle()==null || postEntity.getContent()==null){
            throw new RuntimeException("Invalid arguements");
        }
        return postRepository.save(postEntity);
    }

    public List<PostEntity> retrievePostList(final int postCategory){
        if(!postRepository.existsByPostCategory(postCategory)){
            log.warn("PostCategory doesn't exists {}", postCategory);
            throw new RuntimeException("PostCategory doesn't exists");
        }
        return postRepository.findByPostCategory(postCategory);
    }

    public Optional<?> retrievePost(final String postId){
        return postRepository.findById(postId);
    }

    public List<PostEntity> getPostsByWriter(String writer) {
        return postRepository.findByWriter(writer);
    }
}
