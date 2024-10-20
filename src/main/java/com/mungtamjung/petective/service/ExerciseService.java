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

    public ExerciseEntity startExercise(String userid, LocalDateTime start) {
        ExerciseEntity exerciseEntity = ExerciseEntity.builder()
                .userid(userid)
                .start(start)
                .build();
        return exerciseRepository.save(exerciseEntity);
    }

    public ExerciseEntity endExercise(String userid, ExerciseDTO exerciseDTO) {
        Optional<ExerciseEntity> optionalExerciseEntity = exerciseRepository.findById(exerciseDTO.getId());
        if (!optionalExerciseEntity.isPresent()) {
            throw new RuntimeException("Exercise record not found");
        }

        ExerciseEntity exerciseEntity = optionalExerciseEntity.get();
        exerciseEntity.setPetid(exerciseDTO.getPetid());
        exerciseEntity.setEnd(exerciseDTO.getEnd());
        exerciseEntity.setPath((List<LatLng>) exerciseDTO.getPath());
        exerciseEntity.setMemo(exerciseDTO.getMemo());

        return exerciseRepository.save(exerciseEntity);
    }
}
