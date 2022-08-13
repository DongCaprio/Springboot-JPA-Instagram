package com.cos.photogramstart.domain.user;
//JPA - Java Persistence API (자바로 데이터를 영구적으로 저장(DB)할 수 있는 API를 제공)

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity //DB에 테이블을 생성
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 데이터베이스를 따라간다
	private int id;
	@Column(length = 20, unique = true) //이걸 걸어줘야지 중복값이 안들어간다(중복방지어노테이션)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	private String website; //웹사이트
	private String bio; //자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl; //사진
	private String role; //권한
	
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY) //image.java의 User user의 이름 
	// 즉 user를 써줘야한다(의미하는것:나는 연관관계의 주인이 아니다)
	// 그러므로 테이블에 컬럼을 만들지마!! 라는뜻
	// + User를 select 할 때 해당 User id로 등록된 image들을 다 가져와!
	// Lazy일때는 user를 select 할 때 해당 User id로 등록된 image들을 가져오지마(대신 getImages()함수의 image들이 호출될 때(getImages().get(?) 가져와)
	// Eager = User를 select 할 때 해당 User id로 등록된 image들을 전부 join해서 가져와!!
	//***이것을 "양방향매핑" 이라고한다.
	@JsonIgnoreProperties({"user"}) //이것은 getter호출시(return이나 Restcontoller에서 응답등) Image안에있는 user는 하지마
	//위에껀 자주쓰는 어노테이션!! 이거안쓰면 JPA 무한참조가 걸린다
	private List<Image> images; 
	
	private LocalDateTime createDate;
	
	@PrePersist //DB에 INSERT 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role + ", createDate=" + createDate + "]";
	}
	
	
}
