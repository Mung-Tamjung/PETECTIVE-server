package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.ShelterEntity;
import com.mungtamjung.petective.service.ShelterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("shelter")
public class ShelterController {

    @Autowired
    private ShelterService shelterService;

    @GetMapping()
    public ResponseEntity<?> searchShelters(@RequestParam(required = false) String keyword){
        List<ShelterEntity> shelters = shelterService.retrieveShelter(keyword);
        ResponseDTO responseDTO = new ResponseDTO(true, 200, null, shelters);
        return ResponseEntity.ok().body(responseDTO);

    }
}
