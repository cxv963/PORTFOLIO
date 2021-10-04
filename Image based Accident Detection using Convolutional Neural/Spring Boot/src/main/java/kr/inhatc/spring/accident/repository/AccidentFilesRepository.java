package kr.inhatc.spring.accident.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.accident.entity.AccidentFiles;

public interface  AccidentFilesRepository extends JpaRepository<AccidentFiles, Integer> {

	Optional<AccidentFiles> findById(String id);

	List<AccidentFiles> findAllByOrderByIdDesc();

	//void deleteById(String id);

	AccidentFiles findAllById(String id);

	void deleteById(String id);


}