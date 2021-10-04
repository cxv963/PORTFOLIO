package kr.inhatc.spring.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.board.entity.Boards;
import kr.inhatc.spring.user.entity.Users;

@Repository
// <Users, String> String == id
public interface BoardRepository extends JpaRepository<Boards, Integer>{
	
	//findAll 다 땡겨준다. 
	// findAllByOrderBy{variable}Desc() : variable값을 내림차순 정렬해서 반환. 
	List<Boards> findAllByOrderByBoardIdxDesc();


	Page<Boards> findBytitleContaining(String keyword, Pageable pageable);

	Page<Boards> findBycontentsContaining(String keyword, Pageable pageable);

	Page<Boards> findBycreateIdContaining(String keyword, Pageable pageable);

	//Optional<Users> findById(String string);
	
}
