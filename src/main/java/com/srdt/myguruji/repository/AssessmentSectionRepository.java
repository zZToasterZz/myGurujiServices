package com.srdt.myguruji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.AssessmentSection;

@Repository
public interface AssessmentSectionRepository extends JpaRepository<AssessmentSection, Long>{

	AssessmentSection findAssessmentSectionById(@Param("SectionId") long SectionId);
}
