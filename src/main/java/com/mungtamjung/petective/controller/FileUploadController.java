package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//이미지 업로도 테스트를 위한 컨트롤러
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final S3UploadService s3UploadService;

    @PostMapping("/api/upload")
    public ResponseEntity<List<String>> upload(@RequestPart(name="file") List<MultipartFile> files) throws IOException{
        return ResponseEntity.ok(s3UploadService.saveFile(files));
    }
}
