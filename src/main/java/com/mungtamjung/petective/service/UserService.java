package com.mungtamjung.petective.service;

import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        if(userEntity ==null || userEntity.getEmail()==null || userEntity.getUsername()==null || userEntity.getPassword()==null){
            throw new RuntimeException("Invalid arguements");
        }
        final String email = userEntity.getEmail();
        final String username = userEntity.getUsername();
        final String password = userEntity.getPassword();
        if(userRepository.existsByEmail(email)){
            log.warn("User email already exists {}", email);
            throw new RuntimeException("User email already exists");
        }else if(userRepository.existsByUsername(username)){
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder){
        final UserEntity originalUser = userRepository.findByEmail(email);

        if(originalUser !=null && encoder.matches(password, originalUser.getPassword()) ){
            return originalUser;
        }

        return null;
    }
}
