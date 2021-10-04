package kr.inhatc.spring.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.user.entity.Files;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.FilesRepository;

@Service
public class FilesService {
	
	@Autowired
	FilesRepository filesRepository;
	
	public void save(Files files) {
		Files f = new Files();
		f.setId(files.getId());
		f.setFilename(files.getFilename());
		f.setFileOriName(files.getFileOriName());
		f.setFileurl(files.getFileurl());
		
		filesRepository.save(f);
	}
	
	public Files find(String id) {
		System.out.println("----------------------------------------------파일 db확인----------------------------");
		Optional<Files> optional = filesRepository.findById(id);
		if(optional.isPresent()) {
			Files file = optional.get();
			System.out.println("============================여기"+file.getFilename());
			return file;
		}else {
			throw new NullPointerException();
		}
	}
	
	public List<Files> fileList() {
		List<Files> list = filesRepository.findAllByOrderByIdDesc(); //엔티티에 정의된 이름과 같아야함. 쿼리로 던질 껄 메소드로 만듬.
		return list;
	}

	public void fileDelete(String id) {
		filesRepository.deleteById(id);
		
	}

	public void update(String id, Files file) {
		Files f= filesRepository.findAllById(id);
		f.setFilename(file.getFilename());
		f.setFileOriName(file.getFileOriName());
		f.setFileurl(file.getFileurl());
		filesRepository.save(f);
	}
}