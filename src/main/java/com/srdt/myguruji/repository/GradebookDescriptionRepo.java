package com.srdt.myguruji.repository;

import com.srdt.myguruji.enitity.GradebookDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GradebookDescriptionRepo extends JpaRepository<GradebookDescription, Long> {


    @Transactional
    @Modifying
    int deleteGradebookDescriptionByBatchId(@Param("batchid") long batchid);

    long getCountGradebookDescriptionByBatchId(@Param("batchid") long batchid);

}
