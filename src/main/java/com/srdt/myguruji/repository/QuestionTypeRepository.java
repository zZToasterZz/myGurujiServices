package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srdt.myguruji.enitity.QuestionType;


public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long>{
	List<QuestionType> getactivetype();
}
