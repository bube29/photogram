package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 걸려있는 모든 곳에 생성자를 만들어주는 어노테이션/ final 필드를 DI 할때 사용
@Service // 1. IoC 등록이 됐다는 의미  2. 트랜잭션 관리를 해준다.
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder; // 패스워드 암호화
	
	@Transactional // 아래 메서드가 실행되고 종료될때까지 트랜잭션 관리를 해준다. Write(Insert, Update, delete)할때 사용
	public User 회원가입(User user) { // 외부에서 통신을 통해서 받은 데이터를 User Object에 담는다.
		// 회원가입 진행/ 회원가입을 진행하기 위해서는 Repository가 필요하다.
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 패스워드 암호화
		user.setPassword(encPassword);
		user.setRole("ROLE_USER"); // 관리자 권한은 ROLE_ADMIN
		User userEntity = userRepository.save(user); // 왜 Entity라고 거냐면 -> DB에 save가 된 뒤에 응답 받는다. / 데이베이스에 있는 데이터를 User Object에 담는다.
		return userEntity;
	} 
}