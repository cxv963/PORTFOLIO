package kr.inhatc.spring.fire.controller;

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
import kr.inhatc.spring.fire.entity.FireFiles;
import kr.inhatc.spring.fire.entity.Fires;
import kr.inhatc.spring.fire.service.FireFilesService;
import kr.inhatc.spring.fire.service.FireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@Slf4j
@RequiredArgsConstructor // @Autowired를 빼고 사용할때는 이거 넣고 앞에 final을 넣어줘야함.
public class FireController {
	private final FireService fireService; // 서비스를 만듬.
	public int pagenum;
	public String keywords;
	public int Searchpagenum;
	
	public String principalData(Principal principal) {
		return principal.getName();
	}

	@Autowired
	FireFilesService filesService;

	@Autowired
	private PasswordEncoder encoder;


	@GetMapping("/fire/fireList")
	public void accidentList(Model model, @PageableDefault Pageable pageable, Principal principal) {
		pagenum=pageable.getPageNumber();
		Page<Fires> list = fireService.getFireList(pageable);
		model.addAttribute("list", list);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}

	}

	// 사용자 추가할 때
	@GetMapping("/fire/fireInsert")
	public String fireWrite(Model model, Principal principal) {
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

		model.addAttribute("map", map);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}


		return "fire/fireWrite";
	}

	// 사용자를 추가한 정보를 가져와 db에 저장함..
	@PostMapping("/fire/fireInsert")
	public String fireWrite(Fires fire, HttpServletRequest request, @RequestPart("file1") MultipartFile files,Principal principal)
			throws Exception {
		FireFiles file = new FireFiles();
		System.out.println("+++++++++++++++++++++++++++++++++++" + file.getId());
		String sourceFileName = files.getOriginalFilename();
		String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
		File destinationFile;
		String destinationFileName;
		String fileUrl = "src/main/resources/static/images/";
		String id = principalData(principal);

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

		fire.setId(id);
		fire.setFilename(destinationFileName);
		
		// 비어있지 않다면 저장.
		if (fire != null) {
			fireService.saveFires(fire);
		}
		return "redirect:/fire/fireList";
	}

	
	@RequestMapping(value = "/fire/fireDetail/{id}", method = RequestMethod.GET)
	public String fireDetail(@PathVariable("id") String id, Model model, Principal principal) {
		Fires fire = fireService.fireDetail(id);
		FireFiles file = filesService.find(id);
		model.addAttribute("page",pagenum);
		model.addAttribute("fire", fire); // 앞쪽은 웹에서 쓸 이름, 뒷쪽은 객체이름.
		model.addAttribute("file", file);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}

		System.out.println("============================================>" + fire);
		System.out.println("============================================>" + file);
		return "fire/fireDetail";
	}
	
	
	@RequestMapping(value = "/fire/fireSearchDetail/{id}", method = RequestMethod.GET)
	public String fireSearchDetail(@PathVariable("id") String id, Model model, Principal principal) {
		Fires fire = fireService.fireDetail(id);
		FireFiles file = filesService.find(id);
		model.addAttribute("keyword",keywords);
		model.addAttribute("page",Searchpagenum);
		model.addAttribute("fire", fire); // 앞쪽은 웹에서 쓸 이름, 뒷쪽은 객체이름.
		model.addAttribute("file", file);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}

		System.out.println("============================================>" + fire);
		System.out.println("============================================>" + file);
		return "fire/fireSearchDetail";
	}

	@RequestMapping(value = "/fire/fireUpdate/{id}", method = RequestMethod.POST)
	public String fireUpdate(@PathVariable("id") String id, Fires fire, HttpServletRequest request,
			@RequestPart("file1") MultipartFile files) throws Exception {

		FireFiles file = new FireFiles();
		System.out.println("+++++++++++++++++++++++++++++++++++" + fire.getId());
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
		filesService.update(id, file);

		fire.setFilename(destinationFileName);
		System.out.println("=============================>" + fire);

		// 아이디 설정
		fire.setId(id);
		System.out.println("=============================>" + fire);
		fireService.saveFires(fire);
		return "redirect:/fire/fireList?page="+pagenum;
	}
	
	@RequestMapping(value = "/fire/fireSearchUpdate/{id}", method = RequestMethod.POST)
	public String fireSearchUpdate(@PathVariable("id") String id, Fires fire, HttpServletRequest request,
			@RequestPart("file1") MultipartFile files) throws Exception {

		FireFiles file = new FireFiles();
		System.out.println("+++++++++++++++++++++++++++++++++++" + fire.getId());
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
		filesService.update(id, file);

		fire.setFilename(destinationFileName);
		System.out.println("=============================>" + fire);

		// 아이디 설정
		fire.setId(id);
		System.out.println("=============================>" + fire);
		fireService.saveFires(fire);
		String encodedParam = URLEncoder.encode(keywords, "UTF-8");
		return "redirect:/fire/search?keyword="+encodedParam+"&page="+Searchpagenum;
	}

	@RequestMapping(value = "/fire/fireDelete/{id}", method = RequestMethod.GET)
	public String fireDelete(@PathVariable("id") String id) {
		fireService.fireDelete(id);
		return "redirect:/fire/fireList?page="+pagenum;
	}
	
	@RequestMapping(value = "/fire/fireSearchDelete/{id}", method = RequestMethod.GET)
	public String fireSearchDelete(@PathVariable("id") String id) throws UnsupportedEncodingException {
		fireService.fireDelete(id);
		String encodedParam = URLEncoder.encode(keywords, "UTF-8");
		return "redirect:/fire/search?keyword="+encodedParam+"&page="+Searchpagenum;
	}

	@GetMapping("/fire/search")
	public String search(String keyword, Model model,String searchtype, @PageableDefault Pageable pageable, Principal principal) {

		Page<Fires> search = fireService.pageSearch(keyword, pageable,searchtype);
        keywords=keyword;
        Searchpagenum=pageable.getPageNumber();
        
		model.addAttribute("searchList", search);
		model.addAttribute("keyword", keyword);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}


		return "fire/fireSearch";
	}

}
