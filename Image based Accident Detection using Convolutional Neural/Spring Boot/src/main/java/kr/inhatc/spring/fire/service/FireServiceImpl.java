package kr.inhatc.spring.fire.service;

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
import kr.inhatc.spring.fire.entity.Fires;
import kr.inhatc.spring.fire.repository.FireRepository;



//서비스에 등록하면 여기서 구현
@Service
public class FireServiceImpl implements FireService {

	@Autowired
	FireRepository fireRepository; //레포지토리를 만들고
	
	@Override
	public List<Fires> fireList() {
		List<Fires> list = fireRepository.findAllByOrderByIdDesc(); //엔티티에 정의된 이름과 같아야함. 쿼리로 던질 껄 메소드로 만듬.
		return list;
	}

	@Override
	public void saveFires(Fires fire) {
		fireRepository.save(fire); //내부적으로 구현된 기능이라 레포지토리에 등록을 하지 않음..
	}

	@Override
	public Fires fireDetail(String id) {
		Optional<Fires> optional = fireRepository.findById(id); //있을 수도 없을 수도 있다.
		if(optional.isPresent()) {
			Fires accident = optional.get();
			return accident;
		}else {
			throw new NullPointerException();
		}
	}

	@Override
	public void fireDelete(String id) {
		fireRepository.deleteById(id);
	}

	@Transactional
	public List<Fires> search(String keyword) {
		List<Fires> fireList = fireRepository.findByIdContaining(keyword);
		if(fireList.isEmpty()) {
			fireList = fireRepository.findBytitleContaining(keyword);
			}
        return fireList;
	}

	@Override
	public Page<Fires> getFireList(Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 24, Sort.Direction.DESC, "id");

        return fireRepository.findAll(pageable);
	}

	@Override
	public Page<Fires> pageSearch(String keyword, Pageable pageable, String searchtype) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 24, Sort.Direction.DESC, "id");
        
        if(searchtype.equals("title"))
        {
        	Page<Fires> accidentList=fireRepository.findBytitleContaining(keyword, pageable);
        	 return accidentList;
        }
        else if(searchtype.equals("id"))
        {
        	Page<Fires> accidentList = accidentList = fireRepository.findByIdContaining(keyword, pageable);
        	return accidentList;
        }
		return null;
	}


}
