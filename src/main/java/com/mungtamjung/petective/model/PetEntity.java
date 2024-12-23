package com.mungtamjung.petective.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable=false)
    private String petname;

    @Column(nullable=false)
    private String owner; //user-id(FK)

    @Column(nullable=false)
    private int category; //강아지0,고양이1,기타2


    private String info; //성별,나이,무게,색 등
    private String detail; //성격,특징점


    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "petEntity")
    private List<PetImageEntity> images;

    public void addPetImage(PetImageEntity petImageEntity){
        this.images.add(petImageEntity);
        petImageEntity.setPetEntity(this);
    }
}
