package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ImageUploadDto {
	private MultipartFile file; //파일을 받는 클래스
	private String caption;
}
