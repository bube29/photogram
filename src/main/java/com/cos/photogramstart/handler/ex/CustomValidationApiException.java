package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException {

    // 객체를 구분할때 사용 / 우리한테 중요한건 아님/ JVM에게 중요한것-자바런타임머신에게..
	private static final long serialVersionUID = 1L;

	private Map<String, String> errorMap;
	
	public CustomValidationApiException(String message) {
		super(message);
	}
	
	public CustomValidationApiException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap; 
	}
	
	public Map<String, String> getErrorMap() { // errorMap를 return해주는 Getter
		return errorMap;
	}
}
