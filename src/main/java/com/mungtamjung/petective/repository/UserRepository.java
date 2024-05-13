package com.mungtamjung.petective.repository;


import com.mungtamjung.petective.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUserid(String userid);
    Boolean existsByUserid(String userid);
    UserEntity findByUseridAndPassword(String userid, String Password);

}
