package com.mungtamjung.petective.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    private String id;
    private String userid;
    private String petid;
    private LocalDate date;
    private LocalDateTime start;
    private LocalDateTime end;
    private Point path;
    private String memo;
}
