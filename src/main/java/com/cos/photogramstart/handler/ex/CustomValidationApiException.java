package com.cos.photogramstart.handler.ex;

import java.util.Map;

//런타임익셉션 상속
public class CustomValidationApiException extends RuntimeException{

	//24강 다시보기(23강도)
	
	
	/**
	 *  객체를 구분할때 사용(중요한거아님)
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap;
	
	public CustomValidationApiException(String message) {
		super(message);
	}

	public CustomValidationApiException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}
	
	

}
