package com.srdt.myguruji.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srdt.myguruji.enitity.AssessmentQuestion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AssessmentQuestionRepository extends JpaRepository<AssessmentQuestion, Long>{

    @Transactional
    @Modifying
    int updatequestionid(@Param("quesid") long quesid ,@Param("assessId") long assessId );
}
