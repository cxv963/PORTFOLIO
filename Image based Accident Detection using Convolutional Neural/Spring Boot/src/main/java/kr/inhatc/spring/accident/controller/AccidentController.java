package kr.inhatc.spring.accident.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import kr.inhatc.spring.accident.entity.AccidentFiles;
import kr.inhatc.spring.accident.entity.Accidents;
import kr.inhatc.spring.accident.service.AccidentFilesService;
import kr.inhatc.spring.accident.service.AccidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@Slf4j
@RequiredArgsConstructor // @Autowired를 빼고 사용할때는 이거 넣고 앞에 final을 넣어줘야함.
public class AccidentController {
	private final AccidentService accidentService; // 서비스를 만듬.
	public int pagenum;
	public String keywords;
	public int Searchpagenum;

	@Autowired
	AccidentFilesService filesService;

	@Autowired
	private PasswordEncoder encoder;
	
	public String principalData(Principal principal) {
		return principal.getName();
	}

	@GetMapping("/accident/accidentList")
	public void accidentList(Model model, @PageableDefault Pageable pageable, Principal principal) {
		pagenum=pageable.getPageNumber();
		Page<Accidents> list = accidentService.getAccidentList(pageable);
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
	@GetMapping("/accident/accidentInsert")
	public String accidentWrite(Model model, Principal principal) {
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

		return "accident/accidentWrite";
	}

	// 사용자를 추가한 정보를 가져와 db에 저장함..
	@PostMapping("/accident/accidentInsert")
	public String accidentWrite(Accidents accident, HttpServletRequest request, @RequestPart("file1") MultipartFile files,Principal principal)
			throws Exception {
		AccidentFiles file = new AccidentFiles();
		System.out.println("+++++++++++++++++++++++++++++++++++" + accident.getId());
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
		
		accident.setId(id);
		accident.setFilename(destinationFileName);
		
		// 비어있지 않다면 저장.
		if (accident != null) {
			accidentService.saveAccidents(accident);
		}
		return "redirect:/accident/accidentList";
	}

	// 사용자의 상세정보를 표시함
	@RequestMapping(value = "/accident/accidentDetail/{id}", method = RequestMethod.GET)
	public String accidentDetail(@PathVariable("id") String id, Model model, Principal principal) {
		Accidents accident = accidentService.accidentDetail(id);
		AccidentFiles file = filesService.find(id);
		model.addAttribute("page",pagenum);
		model.addAttribute("accident", accident); // 앞쪽은 웹에서 쓸 이름, 뒷쪽은 객체이름.
		model.addAttribute("file", file);
		
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}
		System.out.println("============================================>" + accident);
		System.out.println("============================================>" + file);
		return "accident/accidentDetail";
	}
	
	@RequestMapping(value = "/accident/accidentSearchDetail/{id}", method = RequestMethod.GET)
	public String accidentSearchDetail(@PathVariable("id") String id, Model model) {
		Accidents accident = accidentService.accidentDetail(id);
		AccidentFiles file = filesService.find(id);
		model.addAttribute("keyword",keywords);
		model.addAttribute("page",Searchpagenum);
		model.addAttribute("accident", accident); // 앞쪽은 웹에서 쓸 이름, 뒷쪽은 객체이름.
		model.addAttribute("file", file);
		System.out.println("============================================>" + accident);
		System.out.println("============================================>" + file);
		return "accident/accidentSearchDetail";
	}

	@RequestMapping(value = "/accident/accidentUpdate/{id}", method = RequestMethod.POST)
	public String userUpdate(@PathVariable("id") String id, Accidents accident, HttpServletRequest request,
			@RequestPart("file1") MultipartFile files) throws Exception {

		AccidentFiles file = new AccidentFiles();
		System.out.println("+++++++++++++++++++++++++++++++++++" + accident.getId());
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

		accident.setFilename(destinationFileName);
		System.out.println("=============================>" + accident);

		// 아이디 설정
		accident.setId(id);
		System.out.println("=============================>" + accident);
		accidentService.saveAccidents(accident);
		return "redirect:/accident/accidentList?page="+pagenum;
	}
	
	@RequestMapping(value = "/accident/accidentSearchUpdate/{id}", method = RequestMethod.POST)
	public String accidentSearchUpdate(@PathVariable("id") String id, Accidents accident, HttpServletRequest request,
			@RequestPart("file1") MultipartFile files) throws Exception {

		AccidentFiles file = new AccidentFiles();
		System.out.println("+++++++++++++++++++++++++++++++++++" + accident.getId());
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

		accident.setFilename(destinationFileName);
		System.out.println("=============================>" + accident);

		// 아이디 설정
		accident.setId(id);
		System.out.println("=============================>" + accident);
		accidentService.saveAccidents(accident);
		String encodedParam = URLEncoder.encode(keywords, "UTF-8");
		
		return "redirect:/accident/search?keyword="+encodedParam+"&page="+Searchpagenum;
	}

	@RequestMapping(value = "/accident/accidentDelete/{id}", method = RequestMethod.GET)
	public String accidentDelete(@PathVariable("id") String id) {
		accidentService.accidentDelete(id);
		return "redirect:/accident/accidentList?page="+pagenum;
	}
	
	@RequestMapping(value = "/accident/accidentSearchDelete/{id}", method = RequestMethod.GET)
	public String accidentSearchDelete(@PathVariable("id") String id) throws UnsupportedEncodingException {
		accidentService.accidentDelete(id);
		String encodedParam = URLEncoder.encode(keywords, "UTF-8");
		return "redirect:/accident/search?keyword="+encodedParam+"&page="+Searchpagenum;
	}

	@GetMapping("/accident/search")
	public String search(String keyword, Model model, String searchtype, @PageableDefault Pageable pageable, Principal principal) {

		Page<Accidents> search = accidentService.pageSearch(keyword, pageable,searchtype);
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

		return "accident/accidentSearch";
	}
}
