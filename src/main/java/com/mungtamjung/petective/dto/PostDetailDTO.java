package com.mungtamjung.petective.dto;

import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.model.PostImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDTO {
    private String id;
    private String writer; //writer의 userid
    private String writer_name; //writer의 username
    private Integer postCategory;
    private Integer petCategory;
    private String title;
    private String content;
    private LocalDate lostDate;
    private LocalDateTime createdAt;
    private String breed;
    private List<String> images; //이미지 url

    public PostDetailDTO toPostDetailDto(PostEntity post){
        List<String> image_urls = new ArrayList<>();
        List<PostImageEntity> images = post.getImages();
        for(PostImageEntity i: images){
            image_urls.add(i.getUrl());
        }
        return PostDetailDTO.builder()
                .id(post.getId())
                .writer(post.getWriter().getId())
                .writer_name(post.getWriter().getUsername())
                .postCategory(post.getPostCategory())
                .petCategory(post.getPetCategory())
                .breed(post.getBreed())
                .title(post.getTitle())
                .content(post.getContent())
                .lostDate(post.getLostDate())
                .images(image_urls)
                .createdAt(post.getCreatedAt())
                .build();
    }
}
