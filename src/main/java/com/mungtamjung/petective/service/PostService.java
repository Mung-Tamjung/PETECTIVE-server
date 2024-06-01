package com.mungtamjung.petective.service;

import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
