package com.mungtamjung.petective.dto;

import com.mungtamjung.petective.model.LatLng;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
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
    private List<LatLng> path;
    private String memo;
}
