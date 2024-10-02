package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.PetDTO;
import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.PetEntity;
import com.mungtamjung.petective.service.PetService;
import com.mungtamjung.petective.service.S3UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("mypet")
public class PetController {
    @Autowired
    private PetService petService;

    @Autowired
    private S3UploadService s3UploadService;

    @PostMapping(value="/register", consumes = {"multipart/form-data"})
    public ResponseEntity<?> registerPet(@RequestPart(value="data") PetDTO petDTO, @RequestPart(name="file") List<MultipartFile> multipartFiles){
//        // 현재 인증된 사용자 정보 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String owner = authentication.getName();

        try{
            PetEntity pet = PetEntity.builder()
                    .petname(petDTO.getPetname())
                    .owner(petDTO.getOwner()) //로그인한 사용자 정보
                    .category(petDTO.getCategory())
                    .info(petDTO.getInfo())
                    .detail(petDTO.getDetail())
                    .images(new ArrayList<>())
                    .build();

            PetEntity registerPet = petService.create(pet, multipartFiles);


            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, registerPet);

            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping
    public ResponseEntity<?> getMyPetList(@RequestParam("owner") String owner){
        try{
            List<PetEntity> petList = petService.retrieveMyPets(owner);

            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, petList);

            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @GetMapping("/{petId}")
    public ResponseEntity<?> getPetDetail(@PathVariable("petId") String petId){
        try{
            Optional<?> pet = petService.retrievePet(petId);
            if(pet==null){
                throw new RuntimeException("Pet doesn't exist");
            }
            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, pet);
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
