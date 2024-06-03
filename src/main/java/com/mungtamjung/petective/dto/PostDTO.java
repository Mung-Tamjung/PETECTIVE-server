package com.mungtamjung.petective.dto;

import com.mungtamjung.petective.model.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String id;
    private String writer;
    private int post_category;
    private int pet_category;
    private String title;
    private String content;
    private LocalDate lost_date;
    //private String lost_location;

    public static PostEntity toEntity(final PostDTO dto){
        return PostEntity.builder()
                .id(dto.getId())
                .writer(dto.getWriter())
                .post_category(dto.getPost_category())
                .pet_category(dto.getPet_category())
                .title(dto.getTitle())
                .content(dto.getContent())
                .lost_date(dto.getLost_date())
                .build();
    }
}
