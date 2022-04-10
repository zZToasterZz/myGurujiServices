package com.srdt.myguruji.repository;

import com.srdt.myguruji.enitity.GradeBookMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GradeBookMarksRepo extends JpaRepository<GradeBookMarks,Long> {
    long checkExisting(@Param("batchid") long batchid,@Param("courseid") long courseid,@Param("pslamtype") String pslamtype,@Param("emplid") String emplid);
    GradeBookMarks getGradebookMarks(@Param("batchid") long batchid,@Param("courseid") long courseid,@Param("pslamtype") String pslamtype,@Param("emplid") String emplid);
    List<GradeBookMarks> getGradebookMarksbyBatchid(@Param("batchid") long batchid,@Param("courseid") long courseid);

    @Transactional
    @Modifying
    int deleteGradeBookMarksByBatchId(@Param("batchid") long batchid);

    long getCountGradeBookMarksByBatchId(@Param("batchid") long batchid);


}
