package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfilDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${file.path}") // yml의 file: path 값을 가져온다 (yml다루는 이거 꼭 기억하기)
	private String uploadFolder;

	@Transactional
	public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {
		UUID uuid = UUID.randomUUID(); // uuid (uuid란 네트워크상에서 고유성이 보장되는 id를 만들기 위한 표준규약)
		// 파일 이름의 중복 방지를 위해서 uuid를 사용한다.

		String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename(); // 실제파일이름 넣기 ex)1.jpg
		System.out.println("이미지 파일이름 : " + imageFileName);

		Path imageFilePath = Paths.get(uploadFolder + imageFileName);

		// try catch 사용하는 이유
		// 통신할때 or I/O(하드디스크에 업로드 or 읽을때) 예외가 발생할 수 있다 예를 들어 1.jpg읽으려고하는데 그 파일이 없는경우 등
		try {
			Files.write(imageFilePath, profileImageFile.getBytes()); // 1. path, 2. imageFile 3. 생략가능
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
		User userEntity = userRepository.findById(principalId).orElseThrow(()->{
			throw new CustomApiException("유저를 찾을 수 없습니다");
		});
		userEntity.setProfileImageUrl(imageFileName);
		return userEntity;
	} //@transactioanl ==> 더티체킹! 즉 save()필요없음

	@Transactional(readOnly = true) // 조회만 할때는(Data변경이 없을때는) readonly=true를 걸어주면 내부연산할것이 줄어든다
	// select만 할때도 @Transactional을 꼭 붙여주자 reanonly=true
	public UserProfilDto 회원프로필(int pageUserId, int principalId) {
		UserProfilDto dto = new UserProfilDto();

		// SELECT * FROM image WHERE userId = :userId;
		User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId); // 1은 페이지주인, -1은 주인이 아님
		dto.setImageCount(userEntity.getImages().size());

		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

		dto.setSubscribeState(subscribeState == 1);
		dto.setSubscribeCount(subscribeCount);

		userEntity.getImages().forEach((image) -> {
			image.setLikeCount(image.getLikes().size());
		});
		return dto;
	}

	@Transactional
	public User 회원수정(int id, User user) {
		// 1. 영속화
//		User userEntity = userRepository.findById(id).get(); //.get()붙인이유 -> findById에서 null을 리턴할수도있으므로
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new CustomValidationApiException("찾을수 없는 ID입니다.");
		});
		// 대표적으로 .get()과 .roElseThrow()를 사용

		// 2. 영속화된 오브젝트를 수정 - 더티체킹(업데이트 완료) --> 알아서 커밋됨
		userEntity.setName(user.getName());
		String rowPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rowPassword);
		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		return userEntity;
	} // 이때 더티체킹이 일어나서 업데이트가 완료된다
}
