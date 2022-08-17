package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Aspect // AOP 처리 어노테이션
@Component
public class ValidationAdvice {

	// AOP관련 어노테이션 -> @Arond
	// execution(* ) -> 1. *자리 뜻이 어떤 함수를 선택할래? 라는뜻 -> public proteced 등등 뭘 고를래? *은 난
	// 다할래 라는뜻 ,, 2. *Controller.*은 모든 컨트롤러의 모든 메서드를 칭한다 3. *(..)은 파라미터가 뭐든 관여하겠다
	// 라는뜻
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		System.out.println("web api 컨트롤러 전에 실행됌");
		// proceedingJoinPoints => profile 함수의 모든 곳에 접근할 수 있는 변수
		// api의 모든 함수보다 먼저실행

		Object[] args = proceedingJoinPoint.getArgs(); // 파라미터 확인
		for (Object arg : args) {
			if (arg instanceof BindingResult) {
				System.out.println("api 유효성 검사를 하는 함수입니다다ㅏ");
				BindingResult bindingResult = (BindingResult) arg;
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<String, String>();
					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					throw new CustomValidationApiException("유효성 검사 실패", errorMap);
				}
			}
		}

		return proceedingJoinPoint.proceed(); // proceedingJoinPoints는 api컨트롤러의 모든 함수의 내용에 접근할수있는 파라미터이다.
		// return 은 무슨 뜻이냐면 api컨트롤러의 모든 함수내용에 접근 후 다시 원래 함수의 기능으로 돌아가라 라는 뜻
		// 왜냐면 ProceedingJoinPoint가 모든 함수보다 먼저 실행되기 때문!! (return은 관여후 다시 원래 흐름으로 돌아가라!
		// 라는뜻)
	}

	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		System.out.println("web 컨트롤러 전에 실행됌");
		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg : args) {
			if (arg instanceof BindingResult) {
				System.out.println("유효성 검사를 하는 함수입니다다ㅏ");
				BindingResult bindingResult = (BindingResult) arg;
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					throw new CustomValidationException("유효성 검사 실패", errorMap);
				}
			}
		}
		return proceedingJoinPoint.proceed();
	}
}
