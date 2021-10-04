package kr.inhatc.spring.fire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.fire.entity.FireFiles;


public interface  FireFilesRepository extends JpaRepository<FireFiles, Integer> {

	Optional<FireFiles> findById(String id);

	List<FireFiles> findAllByOrderByIdDesc();

	//void deleteById(String id);

	FireFiles findAllById(String id);

	void deleteById(String id);


}