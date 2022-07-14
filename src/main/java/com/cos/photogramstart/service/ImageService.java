package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Value("${file.path}") //yml의 file: path 값을 가져온다 (yml다루는 이거 꼭 기억하기)
	private String uploadFolder;
	
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID(); //uuid (uuid란 네트워크상에서 고유성이 보장되는 id를 만들기 위한 표준규약)
		//파일 이름의 중복 방지를 위해서 uuid를 사용한다.
		
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); //실제파일이름 넣기 ex)1.jpg
		System.out.println("이미지 파일이름 : "+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		//try catch 사용하는 이유
		// 통신할때 or I/O(하드디스크에 업로드 or 읽을때) 예외가 발생할 수 있다 예를 들어 1.jpg읽으려고하는데 그 파일이 없는경우 등
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes()); //1. path, 2. imageFile 3. 생략가능
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
