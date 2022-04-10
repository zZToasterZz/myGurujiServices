package com.srdt.myguruji.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.StudentAssessmentStatusDetails;
import com.srdt.myguruji.model.StudentAssessmentStatus;

@Repository
public interface StudentAssessmentStatusRepository extends JpaRepository<StudentAssessmentStatusDetails, Long> {
	
	//StudentAssessmentStatus findStudentAssesmentStatus(@Param("AssessmentId") long AssessmentId,@Param("BatchId") long BatchId, @Param("StudentId") long StudentId);
	StudentAssessmentStatus findStudentAssesmentStatus(@Param("AssessmentId") long AssessmentId, @Param("StudentId") long StudentId);
	
	@Modifying
	@Transactional
	//void updateStudentAssessmentStatusDetail(@Param("StudentId") long StudentId,@Param("BatchId") long BatchId, @Param("AssessmentId") long AssessmentId,@Param("assessmentSubmissionStatus") String assessmentSubmissionStatus, @Param("assesstmentSubmissionTimestamp") Date assesstmentSubmissionTimestamp);
	void updateStudentAssessmentStatusDetail(@Param("StudentId") long StudentId, @Param("AssessmentId") long AssessmentId,@Param("assessmentSubmissionStatus") String assessmentSubmissionStatus, @Param("assesstmentSubmissionTimestamp") Date assesstmentSubmissionTimestamp);
}