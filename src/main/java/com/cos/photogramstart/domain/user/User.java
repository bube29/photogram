package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA - Java Persistence API (자바로 데이터를 영구적으로 저장(DB)할 수 있는 API를 제공)

@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
@Data // Getter, Setter 필요해서..
@Entity // 디비에 테이블을 생성
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다. (자동)
	private int id; // 테이블에 데이터가 하나씩 들어올때마다 1,2,3,4~ 번호가 늘어남. 사용자가 많으면 int로 커버가 불가. 그래서 Long 사용하는데.. 우리는 서비스 할게 아니니깐 int로...
	
	@Column(length = 20, unique = true) // 회원가입에 제약조건을 건다.
	private String username;
	@Column(nullable = false) // null이 가능하나? -> true : 가능, false : 불가능
	private String password;
	@Column(nullable = false)
	private String name;
	private String website; // 웹 사이트
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender; // 성별
	
	private String profileImageUrl; // 프로필 사진
	private String role; // 권한
	
	// 위에 private Sting들이 영속성 컨텍스트에 들어가 있음.
	
	// mappedBy = "user" 란? ① 나는 연관관계의 주인이 아니다. 연관관계의 주인은 Image의 user다. 그러므로 테이블에 칼럼을 만들지마란 의미.
	                                      // ② User를 Select할 때 해당 User id로 등록된 image들을 다 가져오란 의미.
	// LAZY = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마 - 대신 getImages() 함수의 image들이 호출될때 가져와!!
	// EAGER = User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와!!
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // Image 클래스에 @ManyToOne가 걸려있는 변수를 넣어준다. 
	private List<Image> images; // 양방향 매핑                                                                                                                                         
	
	private LocalDateTime createDate; // 이 데이터가 언제 들어왔는지.. 
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행
 	public void createDate() { // 우리가 직접 입력해서 들어갈께 아니라, 자동으로 들어가게 하기 위해..
		this.createDate = LocalDateTime.now();
	}
}
