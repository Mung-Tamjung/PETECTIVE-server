package com.mungtamjung.petective.dto;

import com.mungtamjung.petective.model.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String id;
    private String writer;
    private Integer postCategory;
    private Integer petCategory;
    private String title;
    private String content;
    private LocalDate lostDate;
    //private String lostLocation;

    private String breed;
    private List encoding;

    public PostDTO(final PostEntity entity){
        this.id = entity.getId();
        this.writer = entity.getWriter();
        this.postCategory = entity.getPostCategory();
        this.petCategory = entity.getPetCategory();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.lostDate = entity.getLostDate();
    }

    public static PostEntity toEntity(final PostDTO dto){
        return PostEntity.builder()
                .id(dto.getId())
                .writer(dto.getWriter())
                .postCategory(dto.getPostCategory())
                .petCategory(dto.getPetCategory())
                .title(dto.getTitle())
                .content(dto.getContent())
                .lostDate(dto.getLostDate())
                .build();
    }
}
