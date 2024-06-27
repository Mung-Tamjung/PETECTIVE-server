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

    private String userId;

    @Column(nullable=false)
    private Integer postCategory;

    @Column(nullable=false)
    private Integer petCategory;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String content;

    @Column(nullable=false)
    private String writer;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate lostDate;

    //lostLocation
    //photo
}
