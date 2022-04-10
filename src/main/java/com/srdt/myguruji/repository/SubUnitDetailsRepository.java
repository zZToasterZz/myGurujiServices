package com.srdt.myguruji.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.SubUnitDetails;
import com.srdt.myguruji.enitity.UnitDetails;

@Repository
public interface SubUnitDetailsRepository extends JpaRepository<SubUnitDetails, Long> {
	
	UnitDetails findUnitDetailsById(@Param("UnitId") long UnitId);
    List<SubUnitDetails> findAllSubUnitDetails();
    @Transactional
    void deleteAllBySubUnitId(@Param("SubUnitId") List<Long> SubUnitId);
    List<SubUnitDetails> findAllSubUnitDetailsByUnitId(@Param("UnitId") long UnitId);
    List<SubUnitDetails> findSubUnitByUnitId(@Param("UnitId") long UnitId);
    
}
