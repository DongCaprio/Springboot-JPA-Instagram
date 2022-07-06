package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	
	@NotBlank
	private String name; //필수
	@NotBlank
	private String password; //필수
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	// 조금 위험함. 코드 수정이 필요할 예정
	public User toEntitity() {
		return User.builder()
				.name(name) //이름을 입력안했다면? -> Validation체크
				.password(password) //사용자가 수정시 패스워드를 기재 안했으면? Validation 체크
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
