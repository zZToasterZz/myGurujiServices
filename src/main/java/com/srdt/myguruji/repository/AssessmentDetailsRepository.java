package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.AssessmentDetails;
import com.srdt.myguruji.enitity.AssessmentQuestion;
import com.srdt.myguruji.enitity.AssessmentSubQuestion;
import com.srdt.myguruji.enitity.ScheduledAssessment;
import com.srdt.myguruji.model.Assessment;
import com.srdt.myguruji.model.Batches;
import com.srdt.myguruji.model.Student;

@Repository
public interface AssessmentDetailsRepository extends JpaRepository<AssessmentDetails, Long>
{
	List<Assessment> findAssessment(@Param("CourseId") long CourseId,@Param("CreatedBy") String Emplid);
	AssessmentDetails findAssessmentDetailsById(@Param("AssessmentId") long AssessmentId);
	AssessmentDetails getAssessmentDetailsById(@Param("AssessmentId") long AssessmentId);
	List<AssessmentQuestion> getQuestionByAssessIdAndSecId(@Param("AssessmentId") long AssessmentId,@Param("SectionId") long SectionId);
	List<AssessmentSubQuestion> getSubqustionByAssessmentQuestionId(@Param("AssessmentQuestionId") long AssessmentQuestionId);
	List<ScheduledAssessment> getScheduleByEmplid(@Param("Emplid") String Emplid);
	List<ScheduledAssessment> getTodayScheduleByEmplid(@Param("Emplid") String Emplid);
	ScheduledAssessment getInstructionByAssessmenId(@Param("AssessmentId") long AssessmentId);
	List<Student> getCondidatesByAssessmentId(@Param("AssessmentId") long AssessmentId);
	List<AssessmentQuestion> getQuestionReponceByAssessIdAndSecId(@Param("AssessmentId") long AssessmentId,@Param("SectionId") long SectionId,@Param("Emplid") String Emplid);
	List<AssessmentSubQuestion> getSubqustionResponceByAssessmentQuestionId(@Param("AssessmentQuestionId") long AssessmentQuestionId,@Param("Emplid") String Emplid);
	List<Assessment> findNotScheduledAssessment(@Param("CourseId") long CourseId,@Param("CreatedBy") String Emplid);
	List<Assessment> findAlreadyScheduledAssessment(@Param("CourseId") long CourseId,@Param("CreatedBy") String Emplid);
	List<AssessmentDetails> getConductedAssessmentByCourseId(@Param("Id") long id);
	List<Batches> getBatchesByAssessmentId(@Param("Id") long id);
	
	//List<AssessResult> getAssessmentResponceByBaches(@Param("assessid") long assessid,@Param("batchid") long batchid);
}
