package kr.inhatc.spring.board.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.entity.Boards;
import kr.inhatc.spring.board.repository.BoardRepository;
import kr.inhatc.spring.user.entity.Users;

@Service
public class BoardServiceimpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;

	@Override
	public List<Boards> boardList() {
		// TODO Auto-generated method stub
		List<Boards> list = boardRepository.findAllByOrderByBoardIdxDesc();
		return list;
	}

	@Override
	public void saveBoards(Boards board) {
		boardRepository.save(board); // DB에 저장
	}

	@Override
	public Boards boardDetail(int boardIdx) {
		Optional<Boards> optional = boardRepository.findById(boardIdx);
		// optional이 현재 존재한다면
		if (optional.isPresent()) {
			Boards board = optional.get();
			int hit=board.getHitCnt();
			board.setHitCnt(hit+1);
			boardRepository.save(board);
			return board;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void boardDelete(int id) {
		boardRepository.deleteById(id);
		
	}

	@Override
	public Page<Boards> getBoardList(Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 15, Sort.Direction.DESC, "boardIdx");

        return boardRepository.findAll(pageable);
	}

	@Override
	public Page<Boards> pageSearch(String keyword, Pageable pageable,String searchtype) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 15, Sort.Direction.DESC, "boardIdx");
        
        if(searchtype.equals("title"))
        {
        	Page<Boards> boardList=boardRepository.findBytitleContaining(keyword, pageable);
        	 return boardList;
        }
        else if(searchtype.equals("id"))
        {
        	Page<Boards> boardList=boardRepository.findBycreateIdContaining(keyword, pageable);
        	return boardList;
        }
		return null;
	}

}
