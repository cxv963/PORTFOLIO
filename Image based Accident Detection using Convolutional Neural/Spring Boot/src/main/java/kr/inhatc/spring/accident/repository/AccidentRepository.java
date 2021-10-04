package kr.inhatc.spring.accident.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.accident.entity.Accidents;


//매퍼같은 역활입니다.
@Repository
public interface AccidentRepository extends JpaRepository<Accidents, String> { //실제 쿼리를 던짐. 앞쪽은 엔티티의 이름, 뒤쪽은 엔티티의 자료형

	List<Accidents> findAllByOrderByIdDesc();

	List<Accidents> findByIdContaining(String keyword);

	List<Accidents> findBytitleContaining(String keyword);

	Page<Accidents> findBytitle(String title, Pageable pageable);
	
	Page<Accidents> findByid(String title, Pageable pageable);
	
	// 특정 단어를 포함하는 게시물을 가져옴.
	Page<Accidents> findByIdContaining(String name, Pageable pageable);

	Page<Accidents> findBytitleContaining(String keyword, Pageable pageable);
	
}
