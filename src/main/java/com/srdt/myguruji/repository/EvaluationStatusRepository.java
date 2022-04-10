package com.srdt.myguruji.repository;


import javax.transaction.Transactional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srdt.myguruji.enitity.EvaluationStatus;

public interface EvaluationStatusRepository extends JpaRepository<EvaluationStatus, Long>
{
	@Query("select a from EvaluationStatus a where a.assessmentDetails.AssessmentId = :assessid and a.studentDetails.StudentId = :studentid")
	EvaluationStatus getStatusByAssessmentId(@Param("assessid") long assessmentid, @Param("studentid") long studentid);
	
	@Modifying
	@Transactional
	void updateStatus(@Param("ST")String status, @Param("ID")long statusid);
}