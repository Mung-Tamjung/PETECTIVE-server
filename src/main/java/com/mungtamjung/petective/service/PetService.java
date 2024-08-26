package com.mungtamjung.petective.service;

import com.amazonaws.util.CollectionUtils;
import com.mungtamjung.petective.model.PetEntity;
import com.mungtamjung.petective.model.PetImageEntity;
import com.mungtamjung.petective.model.PostImageEntity;
import com.mungtamjung.petective.repository.PetImageRepository;
import com.mungtamjung.petective.repository.PetRepository;
import com.mungtamjung.petective.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private S3UploadService s3UploadService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetImageRepository petImageRepository;

    @Transactional
    public PetEntity create(final PetEntity petEntity, List<MultipartFile> multipartFiles){
        final String petname = petEntity.getPetname();
        final String owner = petEntity.getOwner();

        if(petEntity == null || petname ==null || owner ==null ){
            throw new RuntimeException("Invalid arguements");
        }

        if(petRepository.existsByPetname(petname)){
            log.warn("Pet name already exists {}", petname);
            throw new RuntimeException("Pet name already exists");
        }

        PetEntity createdPet =  petRepository.save(petEntity);

        if(!CollectionUtils.isNullOrEmpty(multipartFiles)){
            int count = 1;
            for(MultipartFile multipartFile : multipartFiles){
                String related_id = createdPet.getId();
                String filename = "PET/"+related_id+"_" + count;

                try {
                    String url = s3UploadService.saveFile(multipartFile, filename);

                    PetImageEntity petImageEntity = PetImageEntity.builder()
                            .url(url)
                            .build();

                    petImageRepository.save(petImageEntity);
                    createdPet.addPetImage(petImageEntity);
                    count++;
                } catch (IOException e) {
                    throw new RuntimeException("Uploading images failed");
                }

            }

        }

        return petRepository.save(createdPet);
    }

    public PetEntity update(final PetEntity petEntity){
        String petid = petEntity.getId();
        if(petRepository.findById(petid) == null){
            throw new RuntimeException("The pet doesn't exist");
        }
        return petRepository.save(petEntity);
    }

    @Transactional
    public List<PetEntity> retrieveMyPets(final String owner){
        if(!userRepository.existsById(owner)){
            log.warn("User doesn't exist {}", owner);
            throw new RuntimeException("User doesn't exist");
        }

        return petRepository.findByOwner(owner);
    }

    public Optional<?> retrievePet(final String petId){
        return petRepository.findById(petId);
    }
}
