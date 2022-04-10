package com.srdt.myguruji.repository;

import com.srdt.myguruji.enitity.GradeBookLamtypeMapping;
import com.srdt.myguruji.enitity.GradebookFreezeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GradeBookFreezeRepo extends JpaRepository<GradebookFreezeStatus, Long> {

    String findFreezeStatus(@Param("batchid") long batchid, @Param("courseid") long courseId);
    String findPushStatus(@Param("batchid") long batchid, @Param("courseid") long courseId);
    GradebookFreezeStatus getGradebookFreezeStatus(@Param("batchid") long batchid, @Param("courseid") long courseId,@Param("term") String term);
    long checkGradebookFreezeStatus(@Param("batchid") long batchid, @Param("courseid") long courseId,@Param("term") String term);

    @Transactional
    @Modifying
    int deleteGradebookByBatchId(@Param("batchid") long batchid);

    long getCountGradebookByBatchId(@Param("batchid") long batchid);

}
