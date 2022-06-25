package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository를 상속하면 어노테이션이 없어도 IOC등록이 자동적으로 된다.
public interface UserRepository extends JpaRepository<User, Integer>{ //앞에는 오브젝트, 뒤에는 pk의 타입
	
}
