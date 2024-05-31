package com.mungtamjung.petective.service;

import com.mungtamjung.petective.model.PetEntity;
import com.mungtamjung.petective.repository.PetRepository;
import com.mungtamjung.petective.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    public PetEntity create(final PetEntity petEntity){
        final String petname = petEntity.getPetname();
        final String owner = petEntity.getOwner();

        if(petEntity == null || petname ==null || owner ==null ){
            throw new RuntimeException("Invalid arguements");
        }

        if(petRepository.existsByPetname(petname)){
            log.warn("Pet name already exists {}", petname);
            throw new RuntimeException("Pet name already exists");
        }
        return petRepository.save(petEntity);
    }

    public List<PetEntity> retrieveMyPets(final String owner){
        if(!userRepository.existsById(owner)){
            log.warn("User doesn't exist {}", owner);
            throw new RuntimeException("User doesn't exist");
        }

        return petRepository.findByOwner(owner);
    }
}
