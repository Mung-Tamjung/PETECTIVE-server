package com.mungtamjung.petective.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String userid; // 사용자 Id (FK)

    private String petid; // 반려동물 Id (FK)

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date; // 산책 일자

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start; // 산책 시작 시간

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end; // 산책 종료 시간

    @ElementCollection
    private List<Point> path; // 위치 포인트 리스트

    private String memo; // 산책 기록 메모


}
