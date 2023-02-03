package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserControllr {
	
	private final UserService userService;

	@GetMapping("/user/{id}")
	public String id(@PathVariable int id, Model model) { // {id}를 바인딩해서 받을 수 있는 어노테이션 // Model model <- 업로드한 사진을 들고 이동하기 위해서..
		User userEntity = userService.회원프로필(id); // userEntity은 유저의 정보
		model.addAttribute("user", userEntity);
		return "user/profile"; // profile.jsp 페이지로..
	}
	
	@GetMapping("/user/{id}/update") // principal - 접근 주체, 인증 주체.. 인증된 사용자의 오브젝트 변수 명으로 사용하면 좋음.
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) { // 세션 정보 확인 (principalDetails <- 세션 정보가 들어있음)
		//System.out.println("세션 정보 : "+principalDetails.getUser()); // <- 아래 방식보다 이 방식을 추천한다고...
		
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal(); // <- 리턴 타입 오브젝트니까 다운캐스팅 해줘야 함.
		//System.out.println("직접 찾은 세션 정보 : "+mPrincipalDetails.getUser());
		 
		return "user/update";
	}
	
}
	

