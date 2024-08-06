package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.PetDTO;
import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.PetEntity;
import com.mungtamjung.petective.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("mypet")
public class PetController {
    @Autowired
    private PetService petService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPet(@RequestBody PetDTO petDTO){
        //유저 토큰 인증 로직 추가

        try{
            PetEntity pet = PetEntity.builder()
                    .petname(petDTO.getPetname())
                    .owner(petDTO.getOwner())
                    .category(petDTO.getCategory())
                    .info(petDTO.getInfo())
                    .detail(petDTO.getDetail())
                    .build();

            PetEntity registerPet = petService.create(pet);

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
