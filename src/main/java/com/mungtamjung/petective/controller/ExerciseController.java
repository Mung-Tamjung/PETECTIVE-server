package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.ExerciseEntity;
import com.mungtamjung.petective.service.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("myrecord")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    // 날짜별 산책 기록 목록 조회
    @GetMapping("/dailyrecord")
    public ResponseEntity<?> getDailyExerciseRecords(@RequestParam("petid") String petid,
                                                     @RequestParam("year") int year,
                                                     @RequestParam("month") int month,
                                                     @RequestParam("day") int day) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();
        LocalDate date = LocalDate.of(year, month, day);
        List<ExerciseEntity> records = exerciseService.getDailyExerciseRecords(userid, petid, date);
        ResponseDTO responseDTO = new ResponseDTO(true, 200, null, records);
        return ResponseEntity.ok().body(responseDTO);
    }

    // 날짜별 산책 기록 상세 조회
    @GetMapping("/dailyrecord/{recordId}")
    public ResponseEntity<?> getExerciseRecordDetail(@PathVariable("recordId") String recordId) {
        ExerciseEntity record = exerciseService.getExerciseRecordDetail(recordId);
        if (record == null) {
            ResponseDTO responseDTO = new ResponseDTO(false, 404, "Record not found", null);
            return ResponseEntity.status(404).body(responseDTO);
        }
        ResponseDTO responseDTO = new ResponseDTO(true, 200, null, record);
        return ResponseEntity.ok().body(responseDTO);
    }


}
