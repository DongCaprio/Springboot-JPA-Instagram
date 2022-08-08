package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String caption; // 사진설명
	private String postImageUrl; // 사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장- DB에 그 저장된 경로를 insert

	@JsonIgnoreProperties({"images"}) //user안에있는 images들은 가져오지마!
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	//이미지좋아요
	@JsonIgnoreProperties({"image"}) 
	@OneToMany(mappedBy = "image") //옆에 mappedBy 적으면 "나는 연관관계 주인이 아니에요 포린키 만들지마세요!!!" 라는뜻이다
	//mappedBy 옆에 image는 뭐냐면 밑에 Likes안에있는 private Image image의 변수이름
	private List<Likes> likes;
	
	//댓글
	@OrderBy("id DESC")
	@OneToMany(mappedBy = "image") //fk는 comment안의 image에 만들어진다는뜻
	@JsonIgnoreProperties({"image"})
	private List<Comment> comments;
	
	private LocalDateTime createDate;
	
	@Transient //해당 어노테이션 중요**  이걸 적으면 DB에는 컬럼이 생성되지 않는다
	private boolean likeState;
	
	@Transient //해당 어노테이션 중요**  이걸 적으면 DB에는 컬럼이 생성되지 않는다
	private int likeCount;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
	
}
