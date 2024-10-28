package com.mungtamjung.petective.dto;

import com.mungtamjung.petective.model.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSimpleDTO { //게시글 목록 조회에 이용
    private String id;
    private String writer_name;
    private Integer postCategory;
    private String title;
    private LocalDateTime createdAt;
    private LocalDate lostDate;
    private String image; //썸네일 이미지 url 하나만


    public static Page<PostSimpleDTO> toDtoList(Page<PostEntity> postList){
        Page<PostSimpleDTO> postDtoList = postList.map(m -> m.toPostSimpleDto(m));
        return postDtoList;
    }
}
