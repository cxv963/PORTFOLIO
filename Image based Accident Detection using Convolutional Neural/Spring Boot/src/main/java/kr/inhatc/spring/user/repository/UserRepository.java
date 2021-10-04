package kr.inhatc.spring.user.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.domain.Criteria;
import kr.inhatc.spring.user.entity.Users;

//매퍼같은 역활입니다.
@Repository
public interface UserRepository extends JpaRepository<Users, String> { //실제 쿼리를 던짐. 앞쪽은 엔티티의 이름, 뒤쪽은 엔티티의 자료형

	List<Users> findAllByOrderByIdDesc();

	List<Users> findByIdContaining(String keyword);

	List<Users> findBynameContaining(String keyword);

	List<Users> findByemailContaining(String keyword);

	List<Users> findByroleContaining(String keyword);


	Page<Users> findByidContaining(String keyword, Pageable pageable);

	Page<Users> findByemailContaining(String keyword, Pageable pageable);

	Page<Users> findByroleContaining(String keyword, Pageable pageable);

	Page<Users> findBynameContaining(String keyword, Pageable pageable);






}
