package com.srdt.myguruji.enitity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "FeedbackParameter",uniqueConstraints = @UniqueConstraint(columnNames = {"FeedbackParentMappingId","FeedbackChildId","FeedbackQuestion"}))
@NamedQueries({
        @NamedQuery(name="FeedbackParameter.getFeedbackParameterById",query="select fbp from FeedbackParameter fbp where fbp.FeedbackParameterId=:fpid"),
        @NamedQuery(name="FeedbackParameter.getFeedbackParameterByParentId",query="select fbp from FeedbackParameter fbp where fbp.FeedbackParentId.FeedbackGradingParentMappingId=:fpid"),
})
public class FeedbackParameter extends SharedField implements Serializable {

    @Id
    @SequenceGenerator(allocationSize = 1,initialValue=1,name = "FeedbackParameter_Sqr",sequenceName="FeedbackParameter_Sqr")
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="FeedbackParameter_Sqr")
    private long FeedbackParameterId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FeedbackParentMappingId",referencedColumnName="FeedbackGradingParentMappingId",nullable = false,insertable=true,updatable=true)
    @NotNull
    private FeedbackGradingParentMapping FeedbackParentId;

    @Column
    private String FeedbackChildId;

    @Lob
    @Column(length=16777000,nullable=false)
    @NotBlank
    private String FeedbackQuestion;


    public FeedbackParameter() {
    }

    public FeedbackParameter(long feedbackParameterId, @NotNull FeedbackGradingParentMapping feedbackParentId, String feedbackChildId, @NotBlank String feedbackQuestion,String createdBy) {
        FeedbackParameterId = feedbackParameterId;
        FeedbackParentId = feedbackParentId;
        FeedbackChildId = feedbackChildId;
        FeedbackQuestion = feedbackQuestion;
        setModifiedBy(createdBy);
        setCreatedBy(createdBy);
    }

    public long getFeedbackParameterId() {
        return FeedbackParameterId;
    }

    public void setFeedbackParameterId(long feedbackParameterId) {
        FeedbackParameterId = feedbackParameterId;
    }

    public FeedbackGradingParentMapping getFeedbackParentId() {
        return FeedbackParentId;
    }

    public void setFeedbackParentId(FeedbackGradingParentMapping feedbackParentId) {
        FeedbackParentId = feedbackParentId;
    }

    public String getFeedbackChildId() {
        return FeedbackChildId;
    }

    public void setFeedbackChildId(String feedbackChildId) {
        FeedbackChildId = feedbackChildId;
    }

    public String getFeedbackQuestion() {
        return FeedbackQuestion;
    }

    public void setFeedbackQuestion(String feedbackQuestion) {
        FeedbackQuestion = feedbackQuestion;
    }
}
