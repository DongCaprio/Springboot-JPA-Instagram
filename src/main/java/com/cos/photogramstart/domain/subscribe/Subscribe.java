package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(uniqueConstraints = { @UniqueConstraint(name = "subscribe_uk", columnNames = { "fromUserId", "toUserId" }) })
public class Subscribe { //위에@Table은 검색ㄱㄱ, unique를 쌍으로 걸어주기 위해 사용)

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
	private int id;

	@JoinColumn(name = "fromUserId") // 이렇게안하면 언더바형식으로 들어감
	@ManyToOne
	private User fromUser; // 구독하는애

	@JoinColumn(name = "toUserId")
	@ManyToOne
	private User toUser; // 구독받는애

	private LocalDateTime createDate;

	// 네이티브 쿼리 사용시 밑에꺼 안들어감 (적어도 무용지물!!)
	/*	@PrePersist // DB에 INSERT 되기 직전에 실행
		public void createDate() {
			this.createDate = LocalDateTime.now();
		}*/

}
