package com.mungtamjung.petective.service;

import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        if(userEntity ==null || userEntity.getUserid()==null){
            throw new RuntimeException("Invalid arguements");
        }
        final String userid = userEntity.getUserid();
        if(userRepository.existsByUserid(userid)){
            log.warn("Userid already exists {}", userid);
            throw new RuntimeException("Userid already exists");
        }
        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String userid, final String password){
        return userRepository.findByUseridAndPassword(userid, password);
    }
}
