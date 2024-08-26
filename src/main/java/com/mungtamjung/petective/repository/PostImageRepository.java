package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.PostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImageEntity, String>{

}
