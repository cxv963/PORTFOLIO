package kr.inhatc.spring.fire.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.inhatc.spring.fire.entity.Fires;


public interface FireService {

	List<Fires> fireList(); // 리스트 구하기

	void saveFires(Fires fire);// 사용자 생성
	
	Fires fireDetail(String id); // 사용자 상세정보

	void fireDelete(String id); // 사용자 지우기

	List<Fires> search(String keyword);

	Page<Fires> getFireList(Pageable pageable);

	Page<Fires> pageSearch(String keyword, Pageable pageable, String searchtype);

}
