package kr.inhatc.spring.user.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import groovy.util.logging.Slf4j;
import kr.inhatc.spring.user.entity.Files;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;
import kr.inhatc.spring.user.service.FilesService;
import kr.inhatc.spring.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@Slf4j
@RequiredArgsConstructor //@Autowired를 빼고 사용할때는 이거 넣고 앞에 final을 넣어줘야함.
public class UserController {
	private final UserService userService; //서비스를 만듬.
	public int pagenum;
	public String keywords;
	public int Searchpagenum;
	
	@Autowired
	FilesService filesService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public String principalData(Principal principal) {
		return principal.getName();
	}
	
	@RequestMapping("/") //프로그램 시작 시 루트에 담긴 포트번호로 들어왔을시.
	public String hello(Model model, Principal principal) {
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}

		return "./index"; 
	}
	
	//GET(read), POST(create), PUT(update), DELETE(delete) 레스트풀 서비스임.
	@GetMapping("/user/userList")
	public void userList(Model model, @PageableDefault Pageable pageable, Principal principal){
		
		pagenum=pageable.getPageNumber();
		System.out.println("=======================>페이지넘"+pagenum);
		Page<Users> list =userService.getUserList(pageable);
        model.addAttribute("list", list);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}
        //return "/user/userList";
//		List<Users> list = userService.userList();
//		model.addAttribute("list",list);
	}
	
	//사용자 추가할 때
	@GetMapping("/user/userInsert")
	public String userWrite(Model model, Principal principal) {
		List<String> enabledList = new ArrayList<>();
		enabledList.add("가능");
		enabledList.add("불가능");
		
		List<String> authorityList = new ArrayList<>();
		authorityList.add("ROLE_GUEST");
		authorityList.add("ROLE_MEMBER");
		authorityList.add("ROLE_ADMIN");
		
		Map<String, List<String>> map = new HashMap<>();
		map.put("enabledList", enabledList);
		map.put("authorityList", authorityList);
		
		model.addAttribute("map",map);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}
		
		
		return "user/userWrite"; 
	}
	
	//사용자를 추가한 정보를 가져와 db에 저장함..
	@PostMapping("/user/userInsert")
	public String userWrite(Users user,HttpServletRequest request, @RequestPart("file1") MultipartFile files) throws Exception{
		Files file = new Files();
		System.out.println("+++++++++++++++++++++++++++++++++++"+user.getId());
		String sourceFileName = files.getOriginalFilename(); 
        		String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase(); 
        		File destinationFile; 
        		String destinationFileName;
        		String fileUrl = "src/main/resources/static/images/";
        		String id=user.getId();

        		do { 
            			destinationFileName = Long.toString(System.nanoTime()) + "." + sourceFileNameExtension; 
            			destinationFile = new File(fileUrl + destinationFileName); 
        		} while (destinationFile.exists()); 
        
        		destinationFile.getParentFile().mkdirs(); 
        		files.transferTo(destinationFile);
        		
        		file.setId(id);
        		file.setFilename(destinationFileName);
        		file.setFileOriName(sourceFileName);
        		file.setFileurl(fileUrl);
        		filesService.save(file);
        		
        user.setFilename(destinationFileName);
        if(user !=null) {
        	System.out.println("권한부여:"+user.getEnabled());
        	System.out.println("변 경 전 : "+user.getPw());
        	String pw = encoder.encode(user.getPw());
        	System.out.println("변 경 후 : "+pw);
        	user.setPw(pw);
        	userService.saveUsers(user);
        }
		return "redirect:/user/userList";
	}
	
	//사용자의 상세정보를 표시함
	@RequestMapping(value = "/user/userDetail/{id}", method=RequestMethod.GET)
	public String userDetail(@PathVariable("id") String id, Model model, Pageable pageable, Principal principal) {
		
		Users user = userService.userDetail(id);
		Files file = filesService.find(id);
		model.addAttribute("page",pagenum);
		model.addAttribute("user",user);  //앞쪽은 웹에서 쓸 이름, 뒷쪽은 객체이름.
		model.addAttribute("file",file);
		System.out.println("============================================>"+user);
		System.out.println("============================================>"+file);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}
		return "user/userDetail"; 
	}
	
	//검색된 상세정보를 표시함
	@RequestMapping(value = "/user/userSearchDetail/{id}", method=RequestMethod.GET)
	public String userSearchDetail(String keyword,@PathVariable("id") String id, Model model, Principal principal) {
		Users user = userService.userDetail(id);
		Files file = filesService.find(id);
		model.addAttribute("user",user);  //앞쪽은 웹에서 쓸 이름, 뒷쪽은 객체이름.
		model.addAttribute("file",file);
		model.addAttribute("keyword",keywords);
		model.addAttribute("page",Searchpagenum);
		System.out.println("============================================>"+user);
		System.out.println("============================================>"+file);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}
		return "user/userSearchDetail"; 
	}
	
	@RequestMapping(value = "/user/userUpdate/{id}", method=RequestMethod.POST)
	public String userUpdate(@PathVariable("id") String id, Users user,HttpServletRequest request, @RequestPart("file1") MultipartFile files) throws Exception{
		
		Files file = new Files();
		System.out.println("+++++++++++++++++++++++++++++++++++"+user.getId());
		String sourceFileName = files.getOriginalFilename(); 
        		String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase(); 
        		File destinationFile; 
        		String destinationFileName;
        		String fileUrl = "src/main/resources/static/images/";

        		do { 
            			destinationFileName = Long.toString(System.nanoTime()) + "." + sourceFileNameExtension; 
            			destinationFile = new File(fileUrl + destinationFileName); 
        		} while (destinationFile.exists()); 
        
        		destinationFile.getParentFile().mkdirs(); 
        		files.transferTo(destinationFile);
        		
        		file.setId(id);
        		file.setFilename(destinationFileName);
        		file.setFileOriName(sourceFileName);
        		file.setFileurl(fileUrl);
        		filesService.update(id,file);
        		
        user.setFilename(destinationFileName);
		System.out.println("=============================>"+user);
		
		// 아이디 설정
		user.setId(id);
		System.out.println("=============================>"+user);
		userService.saveUsers(user);
		return "redirect:/user/userList?page="+pagenum;
	}
	
	@RequestMapping(value = "/user/userSearchUpdate/{id}", method=RequestMethod.POST)
	public String userSearchUpdate(@PathVariable("id") String id, Users user,HttpServletRequest request, @RequestPart("file1") MultipartFile files) throws Exception{
		
		Files file = new Files();
		System.out.println("+++++++++++++++++++++++++++++++++++"+user.getId());
		String sourceFileName = files.getOriginalFilename(); 
        		String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase(); 
        		File destinationFile; 
        		String destinationFileName;
        		String fileUrl = "src/main/resources/static/images/";

        		do { 
            			destinationFileName = Long.toString(System.nanoTime()) + "." + sourceFileNameExtension; 
            			destinationFile = new File(fileUrl + destinationFileName); 
        		} while (destinationFile.exists()); 
        
        		destinationFile.getParentFile().mkdirs(); 
        		files.transferTo(destinationFile);
        		
        		file.setId(id);
        		file.setFilename(destinationFileName);
        		file.setFileOriName(sourceFileName);
        		file.setFileurl(fileUrl);
        		filesService.update(id,file);
        		
        user.setFilename(destinationFileName);
		System.out.println("=============================>"+user);
		
		// 아이디 설정
		user.setId(id);
		System.out.println("=============================>"+user);
		userService.saveUsers(user);
		String encodedParam = URLEncoder.encode(keywords, "UTF-8");
		return  "redirect:/user/search?keyword="+encodedParam+"&page="+Searchpagenum;
	}
	
	@RequestMapping(value = "/user/userDelete/{id}", method=RequestMethod.GET)
	public String userDelete(@PathVariable("id") String id) {
		userService.userDelete(id);
		return "redirect:/user/userList?page="+pagenum;
	}
	
	@RequestMapping(value = "/user/userSearchDelete/{id}", method=RequestMethod.GET)
	public String userSearchDelete(@PathVariable("id") String id) throws UnsupportedEncodingException {
		userService.userDelete(id);
		String encodedParam = URLEncoder.encode(keywords, "UTF-8");
		return "redirect:/user/search?keyword="+encodedParam+"&page="+Searchpagenum;
	}
	
	@GetMapping("/user/search")
    public String search(String keyword, String searchtype, Model model,@PageableDefault Pageable pageable, Principal principal) {
        
		
        Page<Users> search = userService.pageSearch(keyword,pageable,searchtype);
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
        return "user/userSearch";
    }
	
	@GetMapping({"/user/test","/user/test2"})// 이런식으로 리퀘스트를 안쓰고 간편하게 이동 가능함.
	public void test() {
		
	}
}
