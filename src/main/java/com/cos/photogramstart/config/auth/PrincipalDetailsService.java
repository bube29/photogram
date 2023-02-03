package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // IoC
public class PrincipalDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

	// 1. 패스워드는 알아서 체킹하니까 신경쓸 필요 없다.
	// 2. 리턴이 잘되면 자동으로 UserDetails 타입을 세션을 만든다. 내가 할건 UserDetails 타입으로 리턴해주면 된다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 로그인 요청을 하면 그게 로그인 요청인지 아닌지를 SecurityConfig의 .loginProcessingUrl("/auth/signin")에서 판단을 하는데.. 
		// 내부적으로 UserDetailsService에서 낚아챈다. 고로.. UserDetailsService를 상속받은 PrincipalDetailsService가...
		User userEntity = userRepository.findByUsername(username); // password는 시큐리티가 알아서 비교를 해주기 때문에, username만 있는지 없는지 확인하면 된다.
		
		if(userEntity == null) {
			return null;
		} else {
			return new PrincipalDetails(userEntity); // UserDetails를 상속받은 Object
		}
		
	}

}