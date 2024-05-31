package com.mungtamjung.petective.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {
    private String petname;
    private String owner;
    private int category; //강아지0,고양이1,기타2
    private String info;
    private String detail;
    //사진 추가
}
