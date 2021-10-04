package kr.inhatc.spring.fire.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.fire.entity.FireFiles;
import kr.inhatc.spring.fire.repository.FireFilesRepository;


@Service
public class FireFilesService {
	
	@Autowired
	FireFilesRepository filesRepository;
	
	public void save(FireFiles file) {
		FireFiles f = new FireFiles();
		f.setId(file.getId());
		f.setFilename(file.getFilename());
		f.setFileOriName(file.getFileOriName());
		f.setFileurl(file.getFileurl());
		
		filesRepository.save(f);
	}
	
	public FireFiles find(String id) {
		System.out.println("----------------------------------------------파일 db확인----------------------------");
		Optional<FireFiles> optional = filesRepository.findById(id);
		if(optional.isPresent()) {
			FireFiles file = optional.get();
			System.out.println("============================여기"+file.getFilename());
			return file;
		}else {
			throw new NullPointerException();
		}
	}
	
	public List<FireFiles> fileList() {
		List<FireFiles> list = filesRepository.findAllByOrderByIdDesc(); //엔티티에 정의된 이름과 같아야함. 쿼리로 던질 껄 메소드로 만듬.
		return list;
	}

	public void fileDelete(String id) {
		filesRepository.deleteById(id);
	}

	public void update(String id, FireFiles file) {
		FireFiles f= filesRepository.findAllById(id);
		f.setFilename(file.getFilename());
		f.setFileOriName(file.getFileOriName());
		f.setFileurl(file.getFileurl());
		filesRepository.save(f);
	}


}