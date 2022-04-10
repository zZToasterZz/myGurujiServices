package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.srdt.myguruji.enitity.AssignmentDetails;

@Repository
public interface AssignmentDetailsRepository extends JpaRepository<AssignmentDetails, Long>
{
	
	AssignmentDetails getAssignmentByAssignId(@Param("assignId") long assignId);
	List<AssignmentDetails> getAssignmentByEmpId(@Param("emplId") String emplId,@Param("courseId") long courseId);

	//SHANTANU
	AssignmentDetails findAssignmentById(@Param("id") long id);
}