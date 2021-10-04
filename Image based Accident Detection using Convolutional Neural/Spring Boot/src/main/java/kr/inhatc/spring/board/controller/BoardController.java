package kr.inhatc.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.Service.BoardService;
import kr.inhatc.spring.board.entity.Boards;
import kr.inhatc.spring.user.entity.Users;

//웹사이트를 받아올 때는 @Controller 결과를 바로 받을 떄는 RestController
@Controller // 웹에서는 주로 컨트롤러 사용.
public class BoardController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	public int pagenum;
	public String keywords;
	public int Searchpagenum;
	
	@Autowired
	private BoardService boardService;

	public String principalData(Principal principal) {
		return principal.getName();
	}
	
	@GetMapping("/board/boardList")
	public void boardList(Model model, @PageableDefault Pageable pageable, Principal principal) {
		pagenum=pageable.getPageNumber();
		Page<Boards> list = boardService.getBoardList(pageable);
		model.addAttribute("list", list);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}
	}

	// @RequestMapping(value = "/user/userInsert", method=RequestMethod.GET)
	@GetMapping(value = "/board/boardInsert")
	public String boardWrite(Principal principal) {
		return "board/boardWrite";
	}

	// 저장
	@PostMapping(value = "/board/boardInsert")
	public String boardInsert(Boards board,Principal principal) {		
		board.setCreateId(principalData(principal));
		boardService.saveBoards(board);
		return "redirect:/board/boardList";
	}

	// DB에서 id정보를 들고 넘어온다. 
	@RequestMapping(value = "/board/boardDetail/{boardIdx}", method = RequestMethod.GET)
	public String boardDetail(@PathVariable("boardIdx") int boardIdx, Model model, Principal principal) {
		Boards board = boardService.boardDetail(boardIdx);
		model.addAttribute("page",pagenum);
		model.addAttribute("board", board);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}
		
		System.out.println("==================> " + board);
		return "board/boardDetail";
	}
	
	@RequestMapping(value = "/board/boardSearchDetail/{boardIdx}", method = RequestMethod.GET)
	public String boardSearchDetail(@PathVariable("boardIdx") int boardIdx, Model model, Principal principal) {
		Boards board = boardService.boardDetail(boardIdx);
		model.addAttribute("page",pagenum);
		model.addAttribute("board", board);
		model.addAttribute("keyword",keywords);
		model.addAttribute("page",Searchpagenum);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}
		System.out.println("==================> " + board);
		return "board/boardSearchDetail";
	}
	
	// 수정.
	@RequestMapping(value = "/board/boardUpdate/{boardIdx}", method=RequestMethod.POST)
	public String boardUpdate(@PathVariable("boardIdx") int id, Boards board) {
		System.out.println("=============>" + board);
		// id 설정
		board.setBoardIdx(id);
		boardService.saveBoards(board);
		return "redirect:/board/boardList?page="+pagenum;
	}
	
	// 검색수정.
	@RequestMapping(value = "/board/boardSearchUpdate/{boardIdx}", method=RequestMethod.POST)
	public String boardSearchUpdate(@PathVariable("boardIdx") int id, Boards board) throws UnsupportedEncodingException {
		System.out.println("=============>" + board);
		// id 설정
		board.setBoardIdx(id);
		boardService.saveBoards(board);
		String encodedParam = URLEncoder.encode(keywords, "UTF-8");
		return "redirect:/board/search?keyword="+encodedParam+"&page="+Searchpagenum;
	}

	// 삭제.
	@RequestMapping(value = "/board/boardDelete/{boardIdx}", method=RequestMethod.GET)
	public String boardDelete(@PathVariable("boardIdx") int id) {
		boardService.boardDelete(id);
		return "redirect:/board/boardList?page="+pagenum;
	}
	
	// 검색 삭제.
	@RequestMapping(value = "/board/boardSearchDelete/{boardIdx}", method=RequestMethod.GET)
	public String boardSearchDelete(@PathVariable("boardIdx") int id) throws UnsupportedEncodingException {
		boardService.boardDelete(id);
		String encodedParam = URLEncoder.encode(keywords, "UTF-8");
		return "redirect:/board/search?keyword="+encodedParam+"&page="+Searchpagenum;
	}
	
	@GetMapping("/board/search")
    public String search(String keyword,String searchtype, Model model,@PageableDefault Pageable pageable, Principal principal) {
                
        Page<Boards> search = boardService.pageSearch(keyword,pageable,searchtype);
        keywords=keyword;
        Searchpagenum=pageable.getPageNumber();
        
        model.addAttribute("searchList", search);
        model.addAttribute("keyword",keyword);

		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}
        return "board/boardSearch";
    }
}
