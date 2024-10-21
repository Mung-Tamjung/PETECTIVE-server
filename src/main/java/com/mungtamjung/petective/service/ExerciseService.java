package com.mungtamjung.petective.service;

import com.mungtamjung.petective.dto.ExerciseDTO;
import com.mungtamjung.petective.model.ExerciseEntity;
import com.mungtamjung.petective.model.LatLng;
import com.mungtamjung.petective.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<ExerciseEntity> getDailyExerciseRecords(String userid, LocalDate date) {
        return exerciseRepository.findByUseridAndDate(userid, date);
    }

    public ExerciseEntity getExerciseRecordDetail(String recordId) {
        return exerciseRepository.findById(recordId).orElse(null);
    }

    public ExerciseEntity recordExercise(ExerciseDTO exerciseDTO) {
        ExerciseEntity exerciseEntity = ExerciseEntity.builder()
                .userid(exerciseDTO.getUserid())
                .petid(exerciseDTO.getPetid())
                .date(exerciseDTO.getDate())
                .start(exerciseDTO.getStart())
                .end(exerciseDTO.getEnd())
                .path(exerciseDTO.getPath())
                .memo(exerciseDTO.getMemo())
                .build();

        return exerciseRepository.save(exerciseEntity);
    }
}
