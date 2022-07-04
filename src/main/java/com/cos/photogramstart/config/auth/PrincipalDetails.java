package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private static final long serialVersionUID = 1L;

	//난 유저 오브젝트를 여기에 담고싶음
	private User user;
	
	// 생성자 생성 //PrincipalDetailsService에서 new 유저를 던져주기위한 생성자
	public PrincipalDetails(User user) {
		this.user = user;
	}
	/* 밑에 메소드들은 UserDetails 상속받으면 알아서 오버라이드 해야되는 것들 */
	
	// 리턴이 Collection인 이유 : 권한이 하나가 아닐수도 있음 (예를 들어 한사람이 3개의 권한을 들고있을 수도 있다)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Collection<GrantedAuthority> collector = new ArrayList<>(); //어레이리스트의 부모가 컬랙션이므로 어레이리스트를 선언
		collector.add(()-> {return user.getRole();});
		/*
		 * 이게 위에 람다식으로 간단하게 표현할 수 있다.
		 collector.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return user.getRole();
			}
		});*/
		return collector;
	}

	/*
	 *  밑에 user.get 어쩌구 return 은 다 내가 설정해야 하는것!!
	 */	
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	// 밑에 4개 오버라이드의 return 값 전부 원래 false 인데 다 true 로 바꿔줘야지 정상적인 로그인이 가능하다
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
