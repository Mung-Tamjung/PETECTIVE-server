package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String> {
    List<PostEntity> findByPostCategory(int postCategory);
    Boolean existsByPostCategory(int postCategory);
    List<PostEntity> findByWriter(String writer);

}
