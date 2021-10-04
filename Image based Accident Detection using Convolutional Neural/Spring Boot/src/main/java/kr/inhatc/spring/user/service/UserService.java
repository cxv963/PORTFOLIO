package kr.inhatc.spring.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.inhatc.spring.domain.Criteria;
import kr.inhatc.spring.user.entity.Users;

public interface UserService {

	List<Users> userList(); 						//리스트 구하기

	void saveUsers(Users user); 				//사용자 생성

	Users userDetail(String id); 				//사용자 상세정보

	void userDelete(String id); 				//사용자 지우기
	
	List<Users> search(String keyword);

	Page<Users> getUserList(Pageable pageable);

	Page<Users> pageSearch(String keyword, Pageable pageable, String searchtype);





}
