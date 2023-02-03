package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 걸려있는 모든 곳에 생성자를 만들어주는 어노테이션/ final 필드를 DI 할때 사용
@Controller // 1. IoC 등록이 됐다는 의미  2. 파일을 리턴하는 컨트롤러
public class AuthController { // @Controller가 붙어있으면 스프링이 AuthController를 자기들 컨테이너를 관리하고 있는 메모리에 메모리 객체를 생성해서 로드를 하는데 객체를 생성하기 위해서는
                                                 // 첫번째 조건이 생성자를 실행을 시켜야 하는데..
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	// @Autowired로 불러와도 됨.
	private final AuthService authService; // AuthService 클래스에서 회원가입 진행
	
	//public AuthController(AuthService authService) { // 여기에 생성자가 있음
	// 	this.authService = authService; // IoC에 AuthService 타입이 있으면 authService에 넣어준다. 이걸 의존성 주입이라고 한다.
	//} // 이렇게 적으면 귀찮아 잘 안쓴다.
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	// 회원가입 버튼 클릭 -> /auth/signup -> /auth/signin 리턴
	@PostMapping("/auth/signup")        // SignupDto signupDto를 Validation해서 문제가 있으면 BindingResult bindingResult 한다. / BindingResult는 클래스임.
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key=value (x-www-form-urlencoded 방식) // @Valid <- validation(전처리)을 체크한다는 의미
            // @Controller 이지만, 리턴 타입 앞에 @ResponseBody 되어있으면 데이터를 return한다.
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) { // BindingResult bindingResult의 getFieldErrors() 컬렉션에 오류가 모아진다. for문 돌면서 모아진 오류가 FieldError error에 담긴다.
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidationException("유효성검사 실패함", errorMap); // signupDto 중 하나라도 실패하면, bindingResult에 담긴다.
			                                                                                                                    // bindingResult에 에러가(hasErrors())가 하나라도 있으면, 내가 만단 해시맵(errorMap)에 에러들이 담기고 다 담기면,
			                                                                                                                    // throw해서 Exception을 강제로 발동시켜서, (CustomValidationException <- 이 Exception을..).. 
			                                                                                                                    // errorMap에 데이터를 던진다. 그럼 회원가입이 실행이 안된다.
			// throw new CustomValidationException("유효성검사 실패함", errorMap) <- 이게 실행되면.. Exception이 발동이 된거고.. 발동한 Exception을 ControllerExceptionHandler가 낚아챈다.
			// e.getErrorMap()를 return하고 종료된다.
		} else {
			// log.info(signupDto.toString()); // private String 타입으로 문자열만 받을 수 있어서 toString()을 붙여야함.
			User user = signupDto.toEntity(); // User Object <- SignupDto의 데이터
			// log.info(user.toString());
			User userEntity = authService.회원가입(user);
			System.out.println(userEntity);
			return "auth/signin"; // signup이 성공하면 로그인하기 위해 signin으로..
		}
		
	}
}
