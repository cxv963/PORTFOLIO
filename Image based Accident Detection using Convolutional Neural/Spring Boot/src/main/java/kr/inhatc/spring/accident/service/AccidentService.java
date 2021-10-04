package kr.inhatc.spring.accident.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.inhatc.spring.accident.entity.Accidents;

public interface AccidentService {

	List<Accidents> accidentList(); // 리스트 구하기

	void saveAccidents(Accidents accident); // 사용자 생성

	Accidents accidentDetail(String id); // 사용자 상세정보

	void accidentDelete(String id); // 사용자 지우기

	List<Accidents> search(String keyword);

	Page<Accidents> getAccidentList(Pageable pageable);

	Page<Accidents> pageSearch(String keyword, Pageable pageable, String searchtype);

}
