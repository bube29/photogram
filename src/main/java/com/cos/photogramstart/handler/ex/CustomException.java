package com.cos.photogramstart.handler.ex;

public class CustomException extends RuntimeException {

    // 객체를 구분할때 사용 / 우리한테 중요한건 아님/ JVM에게 중요한것-자바런타임머신에게..
	private static final long serialVersionUID = 1L;
	
	public CustomException(String message) {
		super(message);
	}
}
