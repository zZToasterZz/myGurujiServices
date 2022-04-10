package com.srdt.myguruji.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srdt.myguruji.enitity.DifficultyLevel;
import com.srdt.myguruji.enitity.QuestionDetails;
import com.srdt.myguruji.enitity.QuestionType;
import com.srdt.myguruji.model.QuestionBankCount;

import javax.transaction.Transactional;

@Repository
public interface QuestionDetailsRepository extends JpaRepository<QuestionDetails, Long> {
	
	DifficultyLevel findDiffLevelById(@Param("DiffId") long DiffId);
	QuestionType findQuestionTypeById(@Param("TypeId") long TypeId);
	QuestionDetails findQuestionDetailsById(@Param("QuestionId") long QuestionId);
    List<DifficultyLevel> findAllDifficultyLevel();
    List<QuestionType> findAllQuestionTypes();
    List<QuestionDetails> searchQuestion(@Param("TopicId") String TopicId,@Param("TypeId") String TypeId,
    		                             @Param("CourseId") String CourseId,@Param("DifficultyId") String DifficultyId);
    List<QuestionBankCount> findQuestionBankCount(@Param("TopicId") String TopicId,@Param("TypeId") String TypeId,
                                                  @Param("CourseId") String CourseId);

    @Transactional
    @Modifying
    int setQuestionStatus(@Param("questionid") long questionid);
}
