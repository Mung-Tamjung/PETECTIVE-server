package com.mungtamjung.petective.service;

import com.mungtamjung.petective.model.ExerciseEntity;
import com.mungtamjung.petective.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<ExerciseEntity> getDailyExerciseRecords(String userid, String petid, LocalDate date) {
        return exerciseRepository.findByUseridAndPetidAndDate(userid, petid, date);
    }

    public ExerciseEntity getExerciseRecordDetail(String recordId) {
        return exerciseRepository.findById(recordId).orElse(null);
    }
}
