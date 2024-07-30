package com.mungtamjung.petective.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    //lostLocation
    //photo
    private String image;
}
