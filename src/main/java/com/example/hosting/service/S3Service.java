package com.example.hosting.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

	
	@Value("${spring.cloud.aws.s3.bucket}")
	private String bucket;
	private final S3Presigner s3Preesigner; //의존성 주입
	
	public String createPresignedUrl(String path) {
		 PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucket).key(path).build();
		 PutObjectPresignRequest putObjectPresignRequest = 
				 PutObjectPresignRequest.builder()
				 						.signatureDuration(Duration.ofMinutes(5))
				 						.putObjectRequest(putObjectRequest)
				 						.build();
		 
		 return s3Preesigner.presignPutObject(putObjectPresignRequest).url().toString();
	}
	
	
	
}
