package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;

	@Transactional
	public Comment 댓글쓰기(String content, int imageId, int userId) {

		
		/*		이렇게하면 안된다
				comment.getUser().setId(userId);
				comment.getImage().setId(imageId);
		 	이렇게하면 comment안의 user랑 image등이 null이어서 error가 난다*/
		
		// Tip!! 객체만들어서 넣어버리기(id만 담기위해 생성) / 이렇게안하면 db에서 findbyid한후에 진행해야된다
		// 대신 return시에 image객체와 user객체는 id만 담긴 빈 객체가 return 된다.
		Image image = new Image();
		image.setId(imageId); // id값 넣기위해 객체생성하기

		//user는 username이 필요하므로 DB에서 불러온다
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomApiException("유저 아이디를 찾을 수 없습니다");
		});
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(userEntity);
		return commentRepository.save(comment);
	}

	@Transactional
	public void 댓글삭제(int id) {
		try {
			commentRepository.deleteById(id);
		} catch (Exception e) {
			throw new CustomApiException(e.getMessage());
		}
	}
}
