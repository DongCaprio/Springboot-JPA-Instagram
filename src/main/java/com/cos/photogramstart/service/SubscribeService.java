package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	private final SubscribeRepository subscribeRepository;
	private final EntityManager em; // 모든 Repository는 EntityManager를 구현해서 만들어진것!(얘가 근본)
	// 얘를 쓰는 이유는 우리가 만들쿼리가 타입이 1개가 아니므로 구현체JPA로만 구현할 수가 없다.
	// 그래서 내가 직접 선언해서 쿼리를 쓰고 매핑시켜주는 것이다.
	// 직접쓴쿼리는 바로밑 구독리스트() 안에있다

	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId) {
		// 쿼리준비
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT true FROM subscribe WHERE fromuserId= ? AND toUserId = u.id), 1, 0) subscribeState, ");
		sb.append("if((?= u.id),1,0) AS equalUserState ");
		sb.append("FROM user u JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ? "); // 세미콜론 첨부X
		// 첫번째 물음표 (로그인한 사람의 아이디 => principalId)
		// 물음표 로그인한 사람의 아이디 => principalId
		// 마지막 물음표 => pageUserId(내가 보는사람아아디)
		System.out.println("+++++++++++++++++++++++"+sb);
		// 쿼리완성
		Query query = em.createNativeQuery(sb.toString()).setParameter(1, principalId).setParameter(2, principalId)
				.setParameter(3, pageUserId);
		// 쿼리실행 (qlrm 라이브러리필요 = Dto에 DB결과를 매핑하기위해서)
		JpaResultMapper result = new JpaResultMapper(); // qlrm => 이 라이브러리는 스프링부트에서 제공X, 내가 pom.xml에 직접 넣어줘야됨
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class); // result.list(쿼리, 리턴타입)
		return subscribeDtos;
	}

	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("나 실행되냐???????????????????????????????????????????????????");
			System.out.println(e);
			System.out.println("나 실행되냐???????????????????????????????????????????????????");
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}

	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}

}
