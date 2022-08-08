package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

	/*@Modifying //INSERT, DELETEE, UPDATEE를 네이티브 쿼리로 작성하려면 해당 어노테이션을 붙여주서야한다.
	@Query(value = "INSERT INTO comment(content, imageId, userId, createDate) VALUES(:content, :imageId, :userId, now())", nativeQuery = true)
	int mSave(String content, int imageId, int userId); //@Quary의 " "안에 :뒤에 단어와 일치하는 파라미터가 있다면 쿼리에 들어가게 된다.
	 이건 return 이 int값밖에 안되서 이렇게하면안됨
	 */
	
	}
