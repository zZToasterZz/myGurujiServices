package com.srdt.myguruji.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.LectureSchedule;

@Repository
public interface LectureScheduleRepository extends JpaRepository<LectureSchedule, Long>
{	
	@Modifying
	@Transactional
	void deleteLectureSchedule(@Param("id") long id);
	List<LectureSchedule> getLectureScheduleByCourseId(@Param("id") long id);
	List<LectureSchedule> getLectureScheduleByUnitId(@Param("id") long id);
	List<LectureSchedule> getLectureScheduleByBatchId(@Param("id") long id);
	
	//SANDHYA
		@Modifying
		@Transactional
		int updateLectureSchedule(@Param("dscr")String dscr,@Param("title") String title,@Param("lecId") long lecId);
}