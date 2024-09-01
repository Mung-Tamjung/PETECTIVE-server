package com.mungtamjung.petective.service;

import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.model.PostImageEntity;
import com.mungtamjung.petective.repository.PostImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostImageService {
    @Autowired
    private PostImageRepository postImageRepository;

    public List<PostImageEntity> retrieveBreed(String breed){
        List<PostImageEntity> related = postImageRepository.findByBreed(breed);

        return related;
    }
}
