package com.srdt.myguruji.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.AutoSeq;

@Repository
public interface AutoSeqRepository extends JpaRepository<AutoSeq, Long>{
	 @Transactional
	 @Modifying
     void SetTmplSeq();
	 @Transactional
	 @Modifying
     void SetAssessSeq();
	 
	 AutoSeq findAutoSeqById();
}
