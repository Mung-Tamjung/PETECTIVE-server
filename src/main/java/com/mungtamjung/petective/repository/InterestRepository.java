package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.InterestEntity;
import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<InterestEntity, String> {
    List<InterestEntity> findByUserId(UserEntity userId);
    boolean existsByUserIdAndPostId(UserEntity userId, PostEntity postId);
}
