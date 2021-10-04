package kr.inhatc.spring.board.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.entity.Boards;

public interface BoardService {

	List<Boards> boardList();

	void saveBoards(Boards board);

	Boards boardDetail(int boardIdx);

	void boardDelete(int id);

	Page<Boards> getBoardList(Pageable pageable);

	Page<Boards> pageSearch(String keyword, Pageable pageable, String searchtype);

	
}
