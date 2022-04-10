package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.ScheduledAssessment;

@Repository
public interface ScheduledAssessmentRepository extends JpaRepository<ScheduledAssessment, Long> {
	
	void deleteScheduledAssessmentByAssessmentId(@Param("AssessmentId") long AssessmentId);
	ScheduledAssessment findScheduledAssessmentByAssessmentId(@Param("AssessmentId") long AssessmentId);
	List<ScheduledAssessment> findScheduledAssessmentByCourseId(@Param("CourseId") long CourseId);
	List<ScheduledAssessment> findScheduledAssessmentByBatchId(@Param("BatchId") long BatchId);
	List<ScheduledAssessment> findScheduledAssessmentByStudentId(@Param("StudentId") long StudentId);
	
	List<ScheduledAssessment> getExistingScheduleByBatchId(@Param("BatchId") List<Long> BatchId);
	int getScheduleStatus(@Param("assessid") long assessid);

}
