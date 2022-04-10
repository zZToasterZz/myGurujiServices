package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.AssignmentStudentMapping;

@Repository
public interface AssignmentStudentMapRepository extends JpaRepository<AssignmentStudentMapping, Long> {
	
	int getUnitAssignmentCount(@Param("courseplanid") long courseplanid,@Param("emplId") long emplId,@Param("ids")List<Long> ids);
	int getCoursewiseAssignmentCount(@Param("courseplanid") long courseplanid,@Param("emplId") long emplId);
	List<AssignmentStudentMapping> getStudentAssignment(@Param("studentId") long studentId,@Param("assignId") long assignId);
	List<Long> getStudentMappList(@Param("assignmntId") long assignmntId );
}
