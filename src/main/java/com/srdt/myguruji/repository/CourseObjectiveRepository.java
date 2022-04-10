package com.srdt.myguruji.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.CourseObjectiveDetails;
import com.srdt.myguruji.model.Translate;

@Repository
public interface CourseObjectiveRepository extends  JpaRepository<CourseObjectiveDetails, Long>{

	List<Translate> findCourseObjectiveByCourseId(@Param("CourseId") long CourseId);
	@Transactional
	@Modifying
	void deleteByObjectiveId(@Param("ObjectiveId") List<Long> ObjectiveId);
	
	CourseObjectiveDetails CourseObjectiveById(@Param("ObjectiveId") long ObjectiveId);
	
	void deleteCourseObjectiveMapping(@Param("UnitId") long UnitId,@Param("ObjectiveMapingId") List<Long> mapid);
	
}
