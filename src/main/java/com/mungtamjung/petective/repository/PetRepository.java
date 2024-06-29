package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, String> {

    List<PetEntity> findByOwner(String owner); //사용자의 펫 목록
    PetEntity findByPetname(String petname);
    Boolean existsByPetname(String petname);
}
