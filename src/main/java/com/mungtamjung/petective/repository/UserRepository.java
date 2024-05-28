package com.mungtamjung.petective.repository;


import com.mungtamjung.petective.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    UserEntity findByEmailAndPassword(String email, String Password);

}
