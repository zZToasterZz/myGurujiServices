package com.srdt.myguruji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.StudentContentDowloadStatus;

@Repository
public interface StudentContentDowloadRepo extends JpaRepository<StudentContentDowloadStatus, Long> {

   int getDocDownloadCount(@Param("batchid") long batchid,@Param("crsplanid") long crsplanid,@Param("unid") long unid,@Param("studentid") long studentid);

   int getVideoDownloadCount(@Param("batchid") long batchid, @Param("crsplanid") long crsplanid,@Param("unid") long unid, @Param("studentid") long studentid);
	
}
