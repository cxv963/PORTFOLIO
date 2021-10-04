package kr.inhatc.spring.accident.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.accident.entity.Accidents;
import kr.inhatc.spring.accident.repository.AccidentRepository;
import kr.inhatc.spring.board.entity.Boards;


//서비스에 등록하면 여기서 구현
@Service
public class AccidentServiceImpl implements AccidentService {

	@Autowired
	AccidentRepository accidentRepository; //레포지토리를 만들고
	
	@Override
	public List<Accidents> accidentList() {
		List<Accidents> list = accidentRepository.findAllByOrderByIdDesc(); //엔티티에 정의된 이름과 같아야함. 쿼리로 던질 껄 메소드로 만듬.
		return list;
	}

	@Override
	public void saveAccidents(Accidents accident) {
		accidentRepository.save(accident); //내부적으로 구현된 기능이라 레포지토리에 등록을 하지 않음..
	}

	@Override
	public Accidents accidentDetail(String id) {
		Optional<Accidents> optional = accidentRepository.findById(id); //있을 수도 없을 수도 있다.
		if(optional.isPresent()) {
			Accidents accident = optional.get();
			return accident;
		}else {
			throw new NullPointerException();
		}
	}

	@Override
	public void accidentDelete(String id) {
		accidentRepository.deleteById(id);
	}

	@Transactional
	public List<Accidents> search(String keyword) {
		List<Accidents> accidentList = accidentRepository.findByIdContaining(keyword);
		if(accidentList.isEmpty()) {
			accidentList = accidentRepository.findBytitleContaining(keyword);
			}
        return accidentList;
	}

	@Override
	public Page<Accidents> getAccidentList(Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 24, Sort.Direction.DESC, "id");

        return accidentRepository.findAll(pageable);
	}

	@Override
	public Page<Accidents> pageSearch(String keyword, Pageable pageable, String searchtype) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page,24, Sort.Direction.DESC, "id");
        
        if(searchtype.equals("title"))
        {
        	Page<Accidents> accidentList=accidentRepository.findBytitleContaining(keyword, pageable);
        	 return accidentList;
        }
        else if(searchtype.equals("id"))
        {
        	Page<Accidents> accidentList = accidentRepository.findByIdContaining(keyword, pageable);
        	return accidentList;
        }
		return null;
	}


}
