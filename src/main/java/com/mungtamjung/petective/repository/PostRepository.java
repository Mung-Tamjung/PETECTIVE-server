package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String> {
    List<PostEntity> findByPostCategory(int postCategory);
    Boolean existsByPostCategory(int postCategory);
    List<PostEntity> findByWriter(String writer);

    List<PostEntity> findByBreed(String breed);

    @Query("SELECT p FROM PostEntity p WHERE " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.writer) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "p.postCategory = :category")
    List<PostEntity> searchPostsByCategory(@Param("keyword") String keyword, @Param("category") int category, Pageable pageable);
}
