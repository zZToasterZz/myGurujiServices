package com.srdt.myguruji.repository;

import com.srdt.myguruji.enitity.GradeBookLamtypeMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GradeBookLamtypeRepo extends JpaRepository<GradeBookLamtypeMapping, Long> {


    @Transactional
    @Modifying
    int deleteGradeBookLamtypeMappingByBatchId(@Param("batchid") long batchid);

    long getCountGradeBookLamtypeMappingByBatchId(@Param("batchid") long batchid);
}
