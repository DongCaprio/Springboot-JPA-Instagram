package com.cos.photogramstart.handler.ex;

import java.util.Map;

//런타임익셉션 상속
public class CustomException extends RuntimeException{

	//24강 다시보기(23강도)
	
	
	/**
	 *  객체를 구분할때 사용(중요한거아님)
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomException(String message) {
		super(message);
	}

}
