package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UserService userService;

	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@PathVariable int id, @Valid UserUpdateDto userUpdateDto, // @Valid 어노테이션을 꼭 붙어줘야지
																							// UserUpdateDto클래스의 검증어노테이션
																							// @NotBlank등을 검사하게 된다.
			BindingResult bindingResult, // 꼭 @Valid가 적혀있는 다음 파라메타에 적어야된다(뒤에적어야됨 그래야작동함)
			@AuthenticationPrincipal PrincipalDetails principalDetails) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("+++++++++++++++++++++++++++++++++++++++");
				System.out.println(error.getDefaultMessage());
				System.out.println("+++++++++++++++++++++++++++++++++++++++");
			}
			throw new CustomValidationApiException("회원수정 유효성 검사 실패", errorMap);
		} else {
			System.out.println(userUpdateDto);
			User userEntity = userService.회원수정(id, userUpdateDto.toEntitity());
			principalDetails.setUser(userEntity); // 세션값 변경!! (유저정보 수정후에 다시 유저정보 들어가서 보면 바뀌도록 세션변경!!)
			return new CMRespDto<>(1, "회원수정완료", userEntity);
		}
	}
}
