package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.AssignmentQuestions;

@Repository
public interface AssignmentQuestionRepository extends JpaRepository<AssignmentQuestions, Long>{
	int checkExistingQues(@Param("quesId") long quesId,@Param("assignId") long assignId);
	List<AssignmentQuestions> getAssignmentquestionByAssignId(@Param("assignmentId") long assignmentId);
}
