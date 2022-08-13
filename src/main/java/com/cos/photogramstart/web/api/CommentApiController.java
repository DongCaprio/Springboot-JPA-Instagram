package com.cos.photogramstart.web.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentApiController {
	private final CommentService commentService;
	
	@PostMapping("/api/comment")
	public ResponseEntity<?> commentSave(
			@Valid /*여기에 @Valid어노테이션을 걸어야지 CommetDto에 @NotBlank등이 쓸모있어진다이거 안쓰면 무용지물 (그리고 꼭 이거 바로뒤에 BindingResult를 걸자 */
			@RequestBody CommentDto commentDto, 
			BindingResult bindingResult, //바로 @Valid 뒤에 @BindingResult걸기!!
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		//실제로 여기에 AOP기능으로 유효성 체크 항목이 들어가있다.
		//ValidationAdvice.java 참고
		
		Comment comment = commentService.댓글쓰기(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId()); //content, imageId, userId
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글쓰기 성공",comment),HttpStatus.OK);
	}
	
	@DeleteMapping("/api/comment/{id}")
	public ResponseEntity<?> commentDelete(@PathVariable int id){
		commentService.댓글삭제(id);
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글삭제 성공",null),HttpStatus.OK);
	}
}
