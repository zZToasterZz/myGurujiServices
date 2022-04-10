package com.srdt.myguruji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.AssignmentStatusDetails;

import javax.transaction.Transactional;

@Repository
public interface AssignmentSubmissionStatusRepository extends JpaRepository<AssignmentStatusDetails, Long> 	{//AssignmentStatusDetails{

	String getStudentStatus(@Param("assignmentId") long assignmentId,@Param("studentid") long studentid);
	AssignmentStatusDetails getExistingStudentStatus(@Param("assignmentId") long assignmentId,@Param("studentid") long studentid);

	@Modifying
	@Transactional
	int setEvaluationStatus(@Param("status") String status,@Param("assignmentId") long assignmentId,@Param("studentid") long studentid );
}
