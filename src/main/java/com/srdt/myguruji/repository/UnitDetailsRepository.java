package com.srdt.myguruji.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.CoursePlan;
import com.srdt.myguruji.enitity.UnitDetails;
import com.srdt.myguruji.model.Translate;

@Repository
public interface UnitDetailsRepository extends JpaRepository<UnitDetails, Long> {

	CoursePlan findCoursePlanById(@Param("CoursePlanId") long CoursePlanId);
	@Transactional
	void deleteUnitDetailsByUnitId(@Param("UnitId") List<Long> UnitId);
	List<UnitDetails> findAllUnitDetails();
	List<UnitDetails> findUnitDetailsByCoursePlanId(@Param("CoursePlanId") long CoursePlanId);
	List<Translate> findUnitsByCoursePlanId(@Param("CoursePlanId") long CoursePlanId);
	UnitDetails findUnitByUnitId(@Param("UnitId") long UnitId);
}
