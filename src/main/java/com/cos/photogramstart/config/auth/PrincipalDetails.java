package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private static final long serialVersionUAdminID = 1L;

	private User user; // user 정보를 담기위해 User 클래스를 import 하고..
	
	public PrincipalDetails(User user) { // user 정보를 담는 생성자
		this.user = user;
	}
	
	// 권한 : 한개가 아닐 수 있음. / 여기서는 한개의 권한만 쓸 예정(Admin은 Admin 권한, User는 User 권한..)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { // 권한을 가져오는 메서드. 권한은 User 클래스가 갖고 있다.(role)
		Collection<GrantedAuthority> collecter = new ArrayList<>(); // ArrayList도 Collection이다. (ArrayList < AbstractList < AbstractCollection < Collection < Iterable)
		collecter.add(() -> {return user.getRole();});
		return collecter; 
		} // 아래 메서드를 람다식 표현법으로 바꾼것!!
	// 어차피 목적은 add()안에 메서드를 넣고 싶은건데.. 자바에서는 매개 변수에 함수를 못 넣는다. 자바는 함수가 일급 객체가 아니기때문에..
	// 그래서 인터페이스를 넣어야한다. 인터페이스가 함수를 들고 있으면.. 함수를 파라미터로 전달하고 싶으면 자바에서는 무조건 인터페이스로 넘기게 되어있다. 혹은 클래스 오브젝트를... 
		//collecter.add(new GrantedAuthority() { // collecter.getRole()을 해야한는데.. 안되어서..
		//	 @Override
		//	 public String getAuthority() {
		//		 return user.getRole(); // 여기에 권한을 넣어준다.
		// 	}
		// });
		// return collecter;

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { // 이 계정이 만료가 되었니?
		return true; // 아니 - true, 응 만료되었어 - false / false일 경우 로그인이 안된다.
	}

	@Override
	public boolean isAccountNonLocked() { // 이 계정이 잠겼니?
		return true; // 아니 - true, 응 잠겼어 - false / false일 경우 로그인이 안된다.
	}

	@Override
	public boolean isCredentialsNonExpired() { // 이 계정의 Credentials이 만기되지 않았니? 패스워드가 1년이 지났는데 한번도 안바뀐거 아니니? 
		return true; // false일 경우 로그인이 안된다.
	}

	@Override
	public boolean isEnabled() { // 이 계정이 활성화 되어있니? 오랫동안 로그인하지 않았으면 비활성화되어있을...
		return true; // false일 경우 로그인이 안된다.
	}

}
