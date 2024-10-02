package com.mungtamjung.petective.service;

import com.amazonaws.util.CollectionUtils;
import com.mungtamjung.petective.model.PostImageEntity;
import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.repository.PostImageRepository;
import com.mungtamjung.petective.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private S3UploadService s3UploadService;

    @Autowired
    private PostImageRepository postImageRepository;

    @Transactional
    public PostEntity create(final PostEntity postEntity, List<MultipartFile> multipartFiles) {

        if(postEntity ==null || postEntity.getTitle()==null || postEntity.getContent()==null){
            throw new RuntimeException("Invalid arguements");
        }


        PostEntity createdPost = postRepository.save(postEntity);

        if(!CollectionUtils.isNullOrEmpty(multipartFiles)){
            int count = 1;
            for(MultipartFile multipartFile : multipartFiles){
                String related_id = createdPost.getId();
                String filename = "POST/"+related_id+"_" + count;

                try {
                    String url = s3UploadService.saveFile(multipartFile, filename);

                    PostImageEntity postImageEntity = PostImageEntity.builder()
                            .url(url)
                            .category(createdPost.getPostCategory())
                            //.breed("breed") //종 테스트
                            .build();

                    postImageRepository.save(postImageEntity);
                    createdPost.addPostImage(postImageEntity);
                    count++;
                    log.warn("post images: ",createdPost.getImages());
                } catch (IOException e) {
                    throw new RuntimeException("Uploading images failed");
                }

            }

        }
        return postRepository.save(createdPost);
    }

    public PostEntity update(final PostEntity postEntity){
        String postid = postEntity.getId();
        if(postRepository.findById(postid) == null){
            throw new RuntimeException("The post doesn't exist");
        }


        return postRepository.save(postEntity);
    }

    @Transactional
    public Page<PostEntity> retrievePostList(final int postCategory, Pageable pageable){
        if(!postRepository.existsByPostCategory(postCategory)){
            log.warn("PostCategory doesn't exists {}", postCategory);
            throw new RuntimeException("PostCategory doesn't exists");
        }

        return postRepository.findByPostCategory(postCategory, pageable);
    }
    public Optional<PostEntity> retrievePost(final String postId){
        return postRepository.findById(postId);
    }

    public List<PostEntity> getPostsByWriter(String writer) {
        return postRepository.findByWriter(writer);
    }

    public List<PostEntity> searchLostPosts(String keyword, int petCategory, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<PostEntity> postPage = postRepository.searchPostsByCategory(keyword, 0, petCategory, pageable); // 0: 실종 글
        return postPage.getContent();
    }

    public List<PostEntity> searchFindPosts(String keyword, int petCategory, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<PostEntity> postPage = postRepository.searchPostsByCategory(keyword, 1, petCategory, pageable); // 1: 발견 글
        return postPage.getContent();
    }

    public List<PostEntity> retrieveRelatedPost(String breed){
        return postRepository.findByBreed(breed);
    }
}
