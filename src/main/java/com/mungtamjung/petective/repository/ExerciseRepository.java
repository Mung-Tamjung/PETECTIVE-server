package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, String> {
    List<ExerciseEntity> findByUseridAndPetidAndDate(String userid, String petid, LocalDate date);
}
