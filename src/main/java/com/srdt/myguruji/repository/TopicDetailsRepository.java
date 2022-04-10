package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.TopicDetails;
import com.srdt.myguruji.model.Translate;

@Repository
public interface TopicDetailsRepository extends JpaRepository<TopicDetails, Long> {

	void removeTopics(@Param("TopicId") List<Long> TopicId);
	void deleteTopicDetailsByTopicId(@Param("TopicId") List<Long> TopicId);
	List<Translate> findTopicDetailsByCourseId(@Param("CourseId") long CourseId);
	TopicDetails findTopicDetailsById(@Param("TopicId") long TopicId);
	void deleteByCourseDetails(@Param("TopicId") List<Long> TopicId,@Param("CourseId") long CourseId);
	List<TopicDetails> findTopicsDetailByCourseId(@Param("CourseId") long CourseId);
	List<TopicDetails> findAllTopicDetails();
	List<TopicDetails> findTopicsByCourseIdAndTopicId(@Param("CourseId") long CourseId, @Param("TopicId") List<Long> TopicId);
}
