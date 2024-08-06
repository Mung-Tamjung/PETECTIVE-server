package com.mungtamjung.petective.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> saveFile(List<MultipartFile> multipartFiles) throws IOException{
        List<String> urls = new ArrayList<>();
        for(MultipartFile multipartFile : multipartFiles) {
            String originalFilename = multipartFile.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            amazonS3.putObject(
                    new PutObjectRequest(bucket, originalFilename, multipartFile.getInputStream(), metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            urls.add(amazonS3.getUrl(bucket, originalFilename).toString());
        }
        return urls;
    }

    public List<String> saveFile(List<MultipartFile> multipartFiles, char type, String id) throws IOException{
        List<String> urls = new ArrayList<>();

        //이미지 파일 이름 변환
        //post -> O{postid}_이미지번호순차적배정
        //pet -> E{petid}_이비지번호순차적배정
        String originalFilename=type+id+"_";
        int i=0;

        for(MultipartFile multipartFile : multipartFiles) {

            String filename = originalFilename + Integer.toString(i) +".png";
            i++;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            amazonS3.putObject(
                    new PutObjectRequest(bucket, filename, multipartFile.getInputStream(), metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            urls.add(amazonS3.getUrl(bucket, filename).toString());
        }

        return urls;
    }
}
