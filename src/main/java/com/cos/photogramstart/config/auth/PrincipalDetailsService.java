package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //final
@Service
public class PrincipalDetailsService implements UserDetailsService{

	
	private final UserRepository userRepository;
	
	// 1. 패스워드는 알아서 체킹하므로 신경쓸 필요 없음
	// 2. 리턴이 잘되면 자동으로 UserDetails 타입을 세션을 만들어준다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntitiy = userRepository.findByUsername(username);
		if(userEntitiy == null) {
			return null;
		} else {
			// userEntity를 리턴하면 안되고 UserDetails 타입을 리턴을 해줘야한다.
			// 즉 UserDetails 를 상속받은 클래스를 '내'가 생성해서 리턴해준다. --> 이게 지금은 PrincipalDetails
			return new PrincipalDetails(userEntitiy);
		}
	}
}
