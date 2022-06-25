package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service // 1.IoC 2. 트랜잭션 관리
@RequiredArgsConstructor //final DI 처리
public class AuthService {
	
	private final  BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;

	//어노테이션걸면 이 메소드가 진행될 때 트랜잭션이 작동 Write( Insert, Update, Delete) 할때 트랜잭션을 걸어준다
	@Transactional //스프링프레임워크 @Transactional붙어야된다. 맨위에 나오는것 아님
	public User 회원가입(User user) {
		// 회원가입 진행
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		
		user.setRole("ROLE_USER"); //앞에 ROLE_ 은 JPA필수값 , 관리자는 ROLE_ADMIN 으로 할것
		User userEntity = userRepository.save(user);
		return userEntity;
	}
}
