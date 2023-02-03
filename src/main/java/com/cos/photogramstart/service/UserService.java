package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCrotPasswordEncoder; // 패스워드 암호화를 위해..
	
	@Transactional(readOnly = true) // SELECT / readOnly = true로 두면 변경 감지 연산을 안함. 읽기전용으로 일을 좀 적게 시킴.
	public User 회원프로필(int userId) { // 해당 페이지의 주인 아이디를 받는다.
		// 해당 유저가 들고있는 모든 사진을 가져온다. 쿼리로 본다면 SELECT * FROM image WHERE userId = :userId;
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다."); // 해당 유저를 못 찾으면 Exception을 발동 시킨다.
		});
		System.out.println("=======================================");
		//userEntity.getImages().get(0);
		return userEntity;
	} // userEntity은 유저의 정보
	
	@Transactional
	public User 회원수정(int id, User user) {
		// 1. 영속화
		// findById(id).get()을 붙인 이유 - findById에서 1번으로 유저를 찾을건데 유저가 없으면 null이 리턴. 자바에서는 래핑클래스.. Optional을 만들어준다.
		// Optional - 1. 무조건 찾았어. 걱정마.. get()  2. 못찾았어. Exception 발동시킬께.. orElseThrow()  3. orElse()도 있는데 중요하지 않다고..
		User userEntity = userRepository.findById(id).orElseThrow(() -> {return new CustomValidationApiException("찾을 수 없는 ID입니다.");}); // <- 아래 코드의 람다식 표기법! 
		//User userEntity = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
		//	 @Override
		//	 public IllegalArgumentException get() {
		//	 	return new IllegalArgumentException("찾을 수 없는 ID입니다.");
		//	 }
		//}); 
		                                                                                       
		// 2. 영속화된 오브젝트를 수정 - 더티체킹 (업데이트 완료)
		userEntity.setName(user.getName());
		
		String rawPassword = user.getPassword(); // 받아서..
		String encPassword = bCrotPasswordEncoder.encode(rawPassword); // encPassword <- 해시 
		userEntity.setPassword(encPassword); // 패스워드 암호화
		
		userEntity.setWebsite(user.getWebsite());
		userEntity.setBio(user.getBio());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		return userEntity;
	} // 더티체킹이 일어나서 업데이트가 완료됨.
}
