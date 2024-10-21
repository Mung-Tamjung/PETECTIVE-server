package com.mungtamjung.petective.service;

import com.mungtamjung.petective.model.ShelterEntity;
import com.mungtamjung.petective.repository.ShelterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShelterService {
    @Autowired
    private ShelterRepository shelterRepository;

    public List<ShelterEntity> retrieveShelter(String keyword){
        return shelterRepository.searchShelterByKeyword(keyword);
    }
}
