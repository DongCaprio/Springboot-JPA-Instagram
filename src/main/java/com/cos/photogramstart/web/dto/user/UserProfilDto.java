package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilDto { // 내 프로필이 아닌 경우에는 사진등록이 안뜨게 하려고 클래스생성
	private boolean PageOwnerState;
	private User user;
	private int imageCount;
}
