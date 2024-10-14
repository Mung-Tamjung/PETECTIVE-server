package com.mungtamjung.petective.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id; //자동 지정 id

    @Column(nullable=false)
    private Integer postCategory; // 실종글:0, 찾기글:1

    @Column(nullable=false)
    private Integer petCategory; // 강아지:0, 고양이:1, 기타:2

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String content;

    @Column(nullable=false)
    private String writer; //userId

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate lostDate;

    private String breed;

    @Column(length = 10000)
    @Convert(converter = StringListConverter.class)
    private List<Double> encoding;

    @CreatedDate
    private LocalDateTime createdAt;
    //lostLocation

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "postEntity") //cascade 추가
    //@JoinColumn(name = "post_id")
    private List<PostImageEntity> images;

    public void addPostImage(PostImageEntity postImageEntity){
        this.images.add(postImageEntity);
        postImageEntity.setPostEntity(this);
    }
}
