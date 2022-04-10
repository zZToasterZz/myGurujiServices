package com.srdt.myguruji.repository;

import com.srdt.myguruji.enitity.FeedbackGradingParentMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FeedbackGradingParentMappingRepo extends JpaRepository<FeedbackGradingParentMapping, Long> {

    public String getParentPath(@Param("parentid") long parentid);
    @Modifying
    @Transactional
    int updateParentPath(@Param("parentpath") String parentpath,@Param("mappingId") long mappingId);
    List<FeedbackGradingParentMapping> getFeedbackType();
    FeedbackGradingParentMapping getFeedbackParentById(@Param("id") long id);
    List<FeedbackGradingParentMapping> getFeedbackParent();
    List<FeedbackGradingParentMapping> getFeedbackParentList(@Param("parentId") List<Long> parentId);
    List<FeedbackGradingParentMapping> getFeedbackTypeParent(@Param("parentId") String parentId);
    List<FeedbackGradingParentMapping> getFeedbackTypeChildByParentId(@Param("parentId") String parentId);
}
