package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.BatchDetails;
import com.srdt.myguruji.model.Batches;;

@Repository
public interface BatchRepository extends  JpaRepository<BatchDetails, Long> {
	
	long findBatchCountByCourseId(@Param("CourseId") long CourseId);
	List<Batches> findAllBatches();
	List<Batches> search(@Param("id")String id,@Param("code") String code,@Param("title") String title);;
    BatchDetails findBatchByBatchId(@Param("BatchId") long BatchId);
    BatchDetails findBatchDetailsByBatchId(@Param("BatchId") long BatchId);
    List<Batches> findBatchByCourseId(@Param("CourseId") long CourseId);
    List<Batches> findNotMapBatchByCourseId(@Param("CourseId") long CourseId,@Param("Emplid") String Emplid);
    BatchDetails getBatchByBatchCode(@Param("batchcode") String batchcode);
}
