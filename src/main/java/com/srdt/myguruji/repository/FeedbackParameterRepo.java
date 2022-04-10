package com.srdt.myguruji.repository;

import com.srdt.myguruji.enitity.FeedbackParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackParameterRepo extends JpaRepository<FeedbackParameter, Long> {

    FeedbackParameter getFeedbackParameterById(@Param("fpid") long fpid);
    List<FeedbackParameter> getFeedbackParameterByParentId(@Param("fpid") long fpid);
}
