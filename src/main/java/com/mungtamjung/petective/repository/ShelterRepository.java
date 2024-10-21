package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.ShelterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterRepository extends JpaRepository<ShelterEntity, String> {

    @Query("select s from ShelterEntity s where s.address like concat('%',:keyword,'%') or s.area like concat('%',:keyword,'%') or s.name like concat('%',:keyword,'%') ")
    List<ShelterEntity> searchShelterByKeyword(@Param("keyword") String keyword);
}
