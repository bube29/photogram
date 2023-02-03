package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// Repository - Entity에 의해 생성된 DB에 접근하는 메서드(ex. findAll()) 들을 사용하기 위한 인터페이스
// 어노테이션이 없어도 JpaRepository를 상속하면(인터테이스에서) IoC 등록이 자동으로 된다.
public interface UserRepository extends JpaRepository<User, Integer>{
    // JPA query method (Query creation/ JPA Named Queries)
	User findByUsername(String username); // 찾아서 User Object를 리턴해준다.
}
