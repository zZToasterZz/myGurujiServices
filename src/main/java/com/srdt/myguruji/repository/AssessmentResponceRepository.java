package com.srdt.myguruji.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.srdt.myguruji.enitity.AssessmentResponce;

public interface AssessmentResponceRepository extends JpaRepository<AssessmentResponce, Long>
{
	AssessmentResponce AssessmentResponceById(@Param("Id") long Id);
	@Modifying
	void deleteAssessmentResponce(@Param("Emplid") String Emplid,@Param("QuestionId") long QuestionId,@Param("AssessmentId") long AssessmentId);
	
	@Modifying
	@Transactional
	void setMarks(@Param("MRK")double marks,@Param("AID")long assessmentid, @Param("QID")long questionid, @Param("EID")String emplid);
	
	AssessmentResponce findAssessRecord(@Param("EID")String emplid,@Param("AID")long AssessmentId,@Param("QID")long QuestionId);
}