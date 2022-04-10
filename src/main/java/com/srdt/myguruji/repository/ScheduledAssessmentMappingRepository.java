package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.srdt.myguruji.enitity.ScheduledAssessmentMapping;

public interface ScheduledAssessmentMappingRepository extends JpaRepository<ScheduledAssessmentMapping, Long>
{
	List<ScheduledAssessmentMapping> getBatchByScheduleId(@Param("scheduleid") long scheduleid);
	List<ScheduledAssessmentMapping> getBatchByAssessmentId(@Param("assessmentid") long assessmentid);
}