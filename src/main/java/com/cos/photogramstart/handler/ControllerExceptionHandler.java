package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;

@RestController
@ControllerAdvice //모든 익셉션 다 낙아챈다 (그냥 모든 익셉션 다 받음)
public class ControllerExceptionHandler {

	//handler - ex패키지에 customValidationException만들어줌
	@ExceptionHandler(CustomValidationException.class) // 모든 customValidationException 시 메소드 발동
	public Map<String, String> validationException(CustomValidationException e) {
		return e.getErrorMap();
	}
}
