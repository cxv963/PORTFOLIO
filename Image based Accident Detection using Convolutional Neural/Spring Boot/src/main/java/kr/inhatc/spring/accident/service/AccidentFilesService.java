package kr.inhatc.spring.accident.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.accident.entity.AccidentFiles;
import kr.inhatc.spring.accident.repository.AccidentFilesRepository;

@Service
public class AccidentFilesService {
	
	@Autowired
	AccidentFilesRepository filesRepository;
	
	public void save(AccidentFiles file) {
		AccidentFiles f = new AccidentFiles();
		f.setId(file.getId());
		f.setFilename(file.getFilename());
		f.setFileOriName(file.getFileOriName());
		f.setFileurl(file.getFileurl());
		
		filesRepository.save(f);
	}
	
	public AccidentFiles find(String id) {
		System.out.println("----------------------------------------------파일 db확인----------------------------");
		Optional<AccidentFiles> optional = filesRepository.findById(id);
		if(optional.isPresent()) {
			AccidentFiles file = optional.get();
			System.out.println("============================여기"+file.getFilename());
			return file;
		}else {
			throw new NullPointerException();
		}
	}
	
	public List<AccidentFiles> fileList() {
		List<AccidentFiles> list = filesRepository.findAllByOrderByIdDesc(); //엔티티에 정의된 이름과 같아야함. 쿼리로 던질 껄 메소드로 만듬.
		return list;
	}

	public void fileDelete(String id) {
		filesRepository.deleteById(id);
	}

	public void update(String id, AccidentFiles file) {
		AccidentFiles f= filesRepository.findAllById(id);
		f.setFilename(file.getFilename());
		f.setFileOriName(file.getFileOriName());
		f.setFileurl(file.getFileurl());
		filesRepository.save(f);
	}

}