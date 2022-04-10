package com.srdt.myguruji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srdt.myguruji.enitity.EvaluationStrategy;

import javax.transaction.Transactional;

public interface EvaluationStrategyRepository extends JpaRepository<EvaluationStrategy, Long>
{
	@Query("select a from EvaluationStrategy a where a.assessmentDetails.AssessmentId = :assessid")
	EvaluationStrategy getStrategyByAssessmentId(@Param("assessid")long assessmentid);

	@Modifying
	@Transactional
	int setEvaluationStrategy(@Param("strategy") String strategy,@Param("assessid") long assessid);

	String findEvaluationStrategy(@Param("assessid") long assessid);
}