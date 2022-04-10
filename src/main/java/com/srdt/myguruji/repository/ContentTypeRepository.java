package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.ContentType;
import com.srdt.myguruji.model.ContentCategory;

@Repository
public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
	
	List<ContentType> getContentType();
	ContentType getContentTypeByTypeId(@Param("TypeId") long TypeId);
	List<ContentType> findCourseContentByCourseId(@Param("CourseId") long CourseId);
	List<ContentType> findUnitContentByUnitId(@Param("UnitId") long UnitId);
    List<ContentCategory> courseContentAnalysis(@Param("CourseId") long CourseId);
    List<ContentCategory> unitContentAnalysis(@Param("UnitId") long UnitId);
}