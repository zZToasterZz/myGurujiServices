package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.AssessmentTemplate;

@Repository
public interface AssessmentTemplateRepository extends JpaRepository<AssessmentTemplate, Long>
{
	AssessmentTemplate findTemplateDetailsByTemplateId(@Param("TemplateId") long TemplateId);
	AssessmentTemplate findTemplateDetailsByAssessmentId(@Param("AssessmentId") long AssessmentId);
	List<AssessmentTemplate> findTemplateByEmplid(@Param("CreatedBy") String Emplid);
}