package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data // Getter, Setter를 만들어주는 어노테이션
public class SignupDto {
	
	@Size(min=2, max=20)
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	
	public User toEntity() {
		return User.builder() // 위 4개의 필드 데이터를 기반으로 User 객체가 만들어짐. 
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
}
