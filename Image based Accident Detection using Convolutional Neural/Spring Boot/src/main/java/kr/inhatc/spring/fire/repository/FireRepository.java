package kr.inhatc.spring.fire.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.fire.entity.Fires;



//매퍼같은 역활입니다.
@Repository
public interface FireRepository extends JpaRepository<Fires, String> { //실제 쿼리를 던짐. 앞쪽은 엔티티의 이름, 뒤쪽은 엔티티의 자료형

	List<Fires> findAllByOrderByIdDesc();

	List<Fires> findByIdContaining(String keyword);

	List<Fires> findBytitleContaining(String keyword);

	Page<Fires> findBytitle(String title, Pageable pageable);
	
	Page<Fires> findByid(String title, Pageable pageable);
	
	// 특정 단어를 포함하는 게시물을 가져옴.
	Page<Fires> findByIdContaining(String name, Pageable pageable);

	Page<Fires> findBytitleContaining(String keyword, Pageable pageable);
	
}
