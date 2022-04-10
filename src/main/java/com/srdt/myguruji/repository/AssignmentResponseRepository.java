package com.srdt.myguruji.repository;

import javax.transaction.Transactional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.AssignmentResponse;

@Repository
public interface AssignmentResponseRepository extends JpaRepository<AssignmentResponse, Long>
{
	AssignmentResponse findByResponseId(@Param("id") long id);
	AssignmentResponse findResponseByStudentId(@Param("assignId") long assignId, @Param("studentId") long studentId, @Param("quesId") long quesId);
	
	@Modifying
	@Transactional	
	int saveMarks(@Param("marks") double marks,@Param("assignId") long assignId, @Param("studentId") long studentId, @Param("quesId") long quesId,@Param("responseId")long responseId);
	
	@Query(value="SELECT sum(marks_obtained) FROM assignment_response where assignmentid=:assignId and student_id=:studentId",nativeQuery = true)
	double getObtainedMarks(@Param("assignId") long assignId , @Param("studentId") long studentId);
}