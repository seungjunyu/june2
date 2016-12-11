package net.june.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//public interface UserRepository extends JpaRepository<User, Long>{	//paging query, sorting query
public interface UserRepository extends CrudRepository<User, Long>{	//create, read, update, delete
	User findByUserId(String userId);	//userId를 기반으로 user 데이타를 조회할수 있게된다.
	//User findByUserIdAndName(String userId, String name);
}
