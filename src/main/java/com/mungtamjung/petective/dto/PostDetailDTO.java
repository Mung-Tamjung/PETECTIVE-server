package com.mungtamjung.petective.dto;

import com.mungtamjung.petective.model.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDTO {
    private PostEntity postEntity;
    private List<PostEntity> related;
}
