package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.srdt.myguruji.enitity.CourseBooks;

public interface CourseBookRepository extends JpaRepository<CourseBooks, Long>{

	void deleteCourseBooksByBookId(@Param("BookId") List<Long> BookId);
	List<CourseBooks> findCourseBookByCoursePlanId(@Param("CoursePlanId") long CoursePlanId);
	List<CourseBooks> findAddCourseBooks();
}
