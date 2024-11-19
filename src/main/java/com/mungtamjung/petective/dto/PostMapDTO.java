package com.mungtamjung.petective.dto;

import com.mungtamjung.petective.model.LatLng;
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
public class PostMapDTO {
    private String id;
    private String image;
    private LatLng lostLocation;
    private Integer petCategory;
}
