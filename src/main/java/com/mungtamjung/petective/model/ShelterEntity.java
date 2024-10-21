package com.mungtamjung.petective.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelterEntity {
    @Id
    private String id;

    private String area; //관할구역
    private String name; //보호소명
    private String contact; //연락처
    private String address; //주소
}
