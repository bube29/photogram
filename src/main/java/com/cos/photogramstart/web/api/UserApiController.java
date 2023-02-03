package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService; // service에 있는 회원수정을 호출해야하니깐..

	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id, 
			@Valid UserUpdateDto userUpdateDto, 
			BindingResult bingingResult, // BindingResult bingingResult는 꼭! @Valid가 젹혀있는 다름 파라메터에 적어야됨!!
			@AuthenticationPrincipal PrincipalDetails principalDetails) { // @AuthenticationPrincipal PrincipalDetails principalDetails <- 세션 정보에 접근하는 방법이었음.
		
		if(bingingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bingingResult.getFieldErrors()) { // BindingResult bindingResult의 getFieldErrors() 컬렉션에 오류가 모아진다. for문 돌면서 모아진 오류가 FieldError error에 담긴다.
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidationApiException("유효성검사 실패함", errorMap); // signupDto 중 하나라도 실패하면, bindingResult에 담긴다.
			                                                                                                                    // bindingResult에 에러가(hasErrors())가 하나라도 있으면, 내가 만단 해시맵(errorMap)에 에러들이 담기고 다 담기면,
			                                                                                                                    // throw해서 Exception을 강제로 발동시켜서, (CustomValidationException <- 이 Exception을..).. 
			                                                                                                                    // errorMap에 데이터를 던진다. 그럼 회원가입이 실행이 안된다.
			// throw new CustomValidationException("유효성검사 실패함", errorMap) <- 이게 실행되면.. Exception이 발동이 된거고.. 발동한 Exception을 ControllerExceptionHandler가 낚아챈다.
			// e.getErrorMap()를 return하고 종료된다.
		} else {
			User userEntity =  userService.회원수정(id, userUpdateDto.toEntity()); // User 오브젝트를 보낸다.
			principalDetails.setUser(userEntity); // 세션 정보 변경
			return new CMRespDto<>(1, "회원수정완료", userEntity);
		}
	}
}
