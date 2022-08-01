package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(uniqueConstraints = { @UniqueConstraint(name = "likes_uk", columnNames = { "imageId", "userId" }) })
//위의 중복 컬럼설정이유는 1번유저가 1번 게시물에 좋아요를 1번만 누룰수있지 여러번 누룰수없으므로 중복컬럼설정함
public class Likes { // N

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
	private int id;
	
	
	@JoinColumn(name = "imageId")
	@ManyToOne //manytoone은 기본전략이 eager / onetomany는 기본이 lazy, lazy면 안가져오다가 getter를 호출할때 정보를 불러온다
	private Image image; //1개의 이미지는 좋아요 여러개 가능
	
	@JsonIgnoreProperties({"images"}) 
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user; 
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	}
