package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
@Data // Getter, Setter 필요해서..
@Entity // DB에 테이블을 생성
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다. (자동)
	private int id; // 테이블에 데이터가 하나씩 들어올때마다 1,2,3,4~ 번호가 늘어남. 사용자가 많으면 int로 커버가 불가. 그래서 Long 사용하는데.. 우리는 서비스 할게 아니니깐 int로...
	private String caption; // 설명글
	private String postImageUrl; // 사진을 전송받아서 그 사진을 서버의 특정 폴더에 저장 - DB에 그 저장된 경로를 insert함. DB에 사진을 넣지는 않고, 사진은 서버의 특정 폴더에 저장한다.
	
	@JoinColumn(name="userId") // 포린키의 이름 / DB에 새로운 테이블이 생김(새로운 테이블이 생기면 Repository가 필요함)
	@ManyToOne
	private User user; // 누가 업로드 했는지 알아야 하니깐.. / User user(User Object)로 저장하게 되면 포린키로 저장하게 된다.
	
	// 이미지 좋아요 표시 정보, 댓글 <- 이런 정보도 필요함. 추후 업데이트 예정
	
	private LocalDateTime createDate; // DB에는 항상 시간이 들어가야 한다. 이 데이터가 언제 들어갔는지...
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행
 	public void createDate() { // 우리가 직접 입력해서 들어갈께 아니라, 자동으로 들어가게 하기 위해..
		this.createDate = LocalDateTime.now();
	}

	// 오브젝트를 콘솔에 출력할때 문제가 될 수 있어서 User 부분을 출력되지 않게 함.
    //@Override
	//public String toString() { // 이미 만들어져 있는것. @Data라는 어노테이션을 사용하면 toString()이 자동으로 만들어져 있음.
	// 	return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl // + ", user=" + user <- 이것때문에 무한참조가 일어남.
	//			+ ", createDate=" + createDate + "]";
	//}
	
}
