package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.PetImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetImageRepository extends JpaRepository<PetImageEntity, String> {
}
