package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController // 응답은 데이터를 리턴할꺼라..
@ControllerAdvice // 모든 Exception이 발생할때 낚아채려면 모든 Exception을 다 낚아챈다.
public class ControllerExceptionHandler { // 모든 Exception이 여기에 모인다.

	@ExceptionHandler(CustomValidationException.class) // 발동하는 모든 Exception은 아래 메서드가 가로챈다.
	public String validationException(CustomValidationException e) { // 자바스크립트 응답 (자바스크립트 리턴)
 // CMRespDto와 Script 비교
 // 1. 클라이언트에게 응답할 때는 Script가 좋다. / Script는 응답을 브라우저(클라이언트)가 받는다.
 // 2. Ajax 통신 - CMRespDto / Ajax 통신은 개발자가 자바스크립트 코드로 서버쪽으로 던져서 응답받는 것. 결국 응답을 개발자가 코드로 받는 것.
 // 3. Android 통신 - CMRespDto / Android 통신은 안드로이드 앱에서 개발자가 응답받는 것.		
		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage()); 
		} else {
			return Script.back(e.getErrorMap().toString()); 
		}
		
	//public CMRespDto<?> validationException(CustomValidationException e) {
	//		return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap()); // ErrorMap() 리턴이 아닌 문자열을 리턴하고 싶으면, 
			                     // CMRespDto<Map<String>을 넣고,  ^  이자리에 문자열을 넣으면 된다. CMRespDto이 제네릭 타입이기 때문에...
			// Tip!! CMRespDto<Map<String, String>> <- 여기에 return 타입을 미리 지정하지 않아도 된다. 추후에 추론이 가능하기때문에.. ?를 넣으면 된다.
	}
	
	@ExceptionHandler(CustomException.class) // 발동하는 모든 Exception은 아래 메서드가 가로챈다.
	public String exception(CustomException e) {
		return Script.back(e.getMessage());
		}
	
	@ExceptionHandler(CustomValidationApiException.class) // 발동하는 모든 Exception은 아래 메서드가 가로챈다.
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) { // 오브젝트 응답 (데이터 리턴)
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST); 
		// ResponseEntity를 쓰는 이유 - Http Status 상태코드를 전달받기 위해.. 그래야 update.js 파일에서 done과 fail로 분기시키기 편함.
	}
	
	@ExceptionHandler(CustomApiException.class) // 발동하는 모든 Exception은 아래 메서드가 가로챈다.
	public ResponseEntity<?> apiException(CustomApiException e) { // 오브젝트 응답 (데이터 리턴)
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST); 
		// ResponseEntity를 쓰는 이유 - Http Status 상태코드를 전달받기 위해.. 그래야 update.js 파일에서 done과 fail로 분기시키기 편함.
	}
}