package com.srdt.myguruji.enitity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FeedbackGradingParentMapping",uniqueConstraints = @UniqueConstraint(columnNames = {"FeedbackTypeName","FeedbackParent"}))

@NamedQueries({
        @NamedQuery(name="FeedbackGradingParentMapping.getParentPath", query="select FeedbackParentPath from FeedbackGradingParentMapping where FeedbackGradingParentMappingId=:parentid"),
        @NamedQuery(name="FeedbackGradingParentMapping.getFeedbackType",query="select feedback from FeedbackGradingParentMapping feedback"),
        @NamedQuery(name="FeedbackGradingParentMapping.updateParentPath",query="update FeedbackGradingParentMapping set FeedbackParentPath=:parentpath where FeedbackGradingParentMappingId=:mappingId"),
        @NamedQuery(name="FeedbackGradingParentMapping.getFeedbackParentById", query="select feedback from FeedbackGradingParentMapping feedback where feedback.FeedbackGradingParentMappingId=:id"),
        @NamedQuery(name="FeedbackGradingParentMapping.getFeedbackParent",query="select fb from FeedbackGradingParentMapping fb where fb.FeedbackParent='0'"),
        @NamedQuery(name="FeedbackGradingParentMapping.getFeedbackParentList",query="select fb from FeedbackGradingParentMapping fb where fb.FeedbackGradingParentMappingId in (:parentId)"),
        @NamedQuery(name="FeedbackGradingParentMapping.getFeedbackTypeParent",query="select fb from FeedbackGradingParentMapping fb where fb.FeedbackParentPath like concat(:parentId,'.%') or fb.FeedbackParentPath like :parentId"),
        @NamedQuery(name="FeedbackGradingParentMapping.getFeedbackTypeChildByParentId",query="select fb from FeedbackGradingParentMapping fb where fb.FeedbackParentPath like concat(:parentId,'.%') and  fb.FeedbackParent not like '0'"),
})
public class FeedbackGradingParentMapping extends SharedField implements Serializable {


    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "FeedbackGradingParentMapping_Sqr", sequenceName = "FeedbackGradingParentMapping_Sqr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FeedbackGradingParentMapping_Sqr")
    private long FeedbackGradingParentMappingId;

    @Column(length = 90)
    private String FeedbackTypeName;
    @Column(length = 150)
    private String FeedbackParentPath;
    @Column(length = 80)
    private String FeedbackParent;

    public FeedbackGradingParentMapping() {

    }

    public FeedbackGradingParentMapping(long feedbackGradingParentMappingId, String gradingTypeName, String gradingParentPath, String feedbackParent,String createdBy) {
        FeedbackGradingParentMappingId = feedbackGradingParentMappingId;
        FeedbackTypeName = gradingTypeName;
        FeedbackParentPath = gradingParentPath;
        FeedbackParent = feedbackParent;
        setCreatedBy(createdBy);
        setModifiedBy(createdBy);
    }

    public long getFeedbackGradingParentMappingId() {
        return FeedbackGradingParentMappingId;
    }

    public void setFeedbackGradingParentMappingId(long feedbackGradingParentMappingId) {
        FeedbackGradingParentMappingId = feedbackGradingParentMappingId;
    }

    public String getFeedbackTypeName() {
        return FeedbackTypeName;
    }

    public void setFeedbackTypeName(String feedbackTypeName) {
        FeedbackTypeName = feedbackTypeName;
    }

    public String getFeedbackParentPath() {
        return FeedbackParentPath;
    }

    public void setFeedbackParentPath(String feedbackParentPath) {
        FeedbackParentPath = feedbackParentPath;
    }

    public String getFeedbackParent() {
        return FeedbackParent;
    }

    public void setFeedbackParent(String feedbackParent) {
        FeedbackParent = feedbackParent;
    }
}
