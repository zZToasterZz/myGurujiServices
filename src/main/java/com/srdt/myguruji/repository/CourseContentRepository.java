package com.srdt.myguruji.repository;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.CourseContent;

@Repository
public interface CourseContentRepository extends JpaRepository<CourseContent, Long> {
	
	List<CourseContent> getCourseContentByCourseId(@Param("CourseId") long CourseId);
	@Transactional
	@Modifying
    void deleteCourseContent(@Param("CourseId") List<Long> CourseId);
	BigInteger getCourseContentCount(@Param("CourseId") long CourseId); 
}
