package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
@Data // Getter, Setter 필요해서..
@Entity // 디비에 테이블을 생성
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
						name = "subscribe_uk", // 유니크 제약조건 이름
						columnNames = {"fromUserId", "toUserId"} // 어떤 두개를 제약조건으로 걸꺼냐.. (실제 데이터베이스 테이블의 컬럼명을 적어야함)
				)
		}
)
public class Subscribe { // N:N의 관계 중간 테이블
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다. (자동)
	private int id; // 테이블에 데이터가 하나씩 들어올때마다 1,2,3,4~ 번호가 늘어남. 사용자가 많으면 int로 커버가 불가. 그래서 Long 사용하는데.. 우리는 서비스 할게 아니니깐 int로...
	
	@JoinColumn(name = "fromUserId") // 이렇게 컬럼명을 만들어! 니 맘대로 만들지 말고!!
	@ManyToOne // 자동으로 subscribe 테이블을 생성해준다. 
	private User fromUser;
	
	@JoinColumn(name = "toUserId")
	@ManyToOne
	private User toUser;
	
	
	private LocalDateTime createDate; // 이 데이터가 언제 들어왔는지.. 
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행
 	public void createDate() { // 우리가 직접 입력해서 들어갈께 아니라, 자동으로 들어가게 하기 위해..
		this.createDate = LocalDateTime.now();
	}
}
