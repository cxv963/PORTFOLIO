package kr.inhatc.spring.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.user.entity.Files;
import kr.inhatc.spring.user.entity.Users;

public interface  FilesRepository extends JpaRepository<Files, Integer> {

	Optional<Files> findById(String id);

	List<Files> findAllByOrderByIdDesc();

	void deleteById(String id);

	Files findAllById(String id);

}