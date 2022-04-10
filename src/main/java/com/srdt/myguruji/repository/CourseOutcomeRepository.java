package com.srdt.myguruji.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.CourseOutcome;
import com.srdt.myguruji.model.Translate;

@Repository
public interface CourseOutcomeRepository extends JpaRepository<CourseOutcome, Long> {
	
	List<Translate> getCourseOutcomeByCourseId(@Param("courseid") long courseid);
	@Transactional
	@Modifying
	void removeById(@Param("id") List<Long> ids);

}
