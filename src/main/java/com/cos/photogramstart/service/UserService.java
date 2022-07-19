package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfilDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
