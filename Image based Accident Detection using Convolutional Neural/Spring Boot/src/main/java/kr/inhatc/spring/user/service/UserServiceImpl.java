package kr.inhatc.spring.user.service;

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

import kr.inhatc.spring.domain.Criteria;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;

//서비스에 등록하면 여기서 구현
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository; //레포지토리를 만들고
	
	@Override
	public List<Users> userList() {
		//userRepository.findAll() : 데이터베이스를 전부 가져옴
		//userRepository.findById("Ab") : 데이터 하나만 가져옴
		
		//하나만 잡아올 때는 이거 사용
		//		Optional<Users> result = userRepository.findById("AB");
		//		if(result.isPresent()) { //저장된 값이 null이 아니면.
		//			Users user = result.get();
		//			user.getId();
		//		}
		
		//userRepository로 쿼리 날릴 때 업데이트는 save, 딜리트는 delete
		
		List<Users> list = userRepository.findAllByOrderByIdDesc(); //엔티티에 정의된 이름과 같아야함. 쿼리로 던질 껄 메소드로 만듬.
		return list;
	}

	@Override
	public void saveUsers(Users user) {
		userRepository.save(user); //내부적으로 구현된 기능이라 레포지토리에 등록을 하지 않음..
	}

	@Override
	public Users userDetail(String id) {
		Optional<Users> optional = userRepository.findById(id); //있을 수도 없을 수도 있다.
		if(optional.isPresent()) {
			Users user = optional.get();
			return user;
		}else {
			throw new NullPointerException();
		}
	}

	@Override
	public void userDelete(String id) {
		userRepository.deleteById(id);
	}

	@Transactional
	public List<Users> search(String keyword) {
		List<Users> userList = userRepository.findByIdContaining(keyword);
		if(userList.isEmpty()) {
			userList = userRepository.findBynameContaining(keyword);
			if(userList.isEmpty()) {
				userList = userRepository.findByemailContaining(keyword);
				if(userList.isEmpty()) {
					userList = userRepository.findByroleContaining(keyword);
				}
			}
		}
        return userList;
	}

	@Override
	public Page<Users> getUserList(Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");

        return userRepository.findAll(pageable);
	}

	@Override
	public Page<Users> pageSearch(String keyword, Pageable pageable, String searchtype) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        
        if(searchtype.equals("id"))
        {
        	 Page<Users> userList = userRepository.findByidContaining(keyword, pageable);
        	 return userList;
        }
        else if(searchtype.equals("name"))
        {
        	Page<Users> userList=userRepository.findBynameContaining(keyword, pageable);
        	return userList;
        }
        else if(searchtype.equals("role"))
        {
        	Page<Users> userList=userRepository.findByroleContaining(keyword, pageable);
        	return userList;
        }
		return null;
	}




}
