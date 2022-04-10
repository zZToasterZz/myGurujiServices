package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.AssignmentPlanMapping;

@Repository
public interface AssignmentPlanMappingRepository extends JpaRepository<AssignmentPlanMapping, Long> {

	List<AssignmentPlanMapping> getAssignmentbyCourseplanID(@Param("courseplanId") long courseplanId);
	AssignmentPlanMapping getAssignmentplanbyAssignmentId(@Param("assignId") long assignId);
}
