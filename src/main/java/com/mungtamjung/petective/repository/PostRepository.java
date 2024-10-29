package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String> {
    @Query("SELECT p FROM PostEntity p WHERE p.postCategory = :postCategory ORDER BY p.createdAt DESC")
    Page<PostEntity> findByPostCategory(int postCategory, Pageable pageable);
    Boolean existsByPostCategory(int postCategory);
    List<PostEntity> findByWriter(UserEntity writer);

    List<PostEntity> findByBreed(String breed);

    @Query("SELECT p FROM PostEntity p WHERE " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.writer) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "p.postCategory = :postCategory AND " +
            "p.petCategory = :petCategory")
    Page<PostEntity> searchPostsByCategory(@Param("keyword") String keyword,
                                                         @Param("postCategory") int postCategory,
                                                         @Param("petCategory") int petCategory,
                                                         Pageable pageable);
}
