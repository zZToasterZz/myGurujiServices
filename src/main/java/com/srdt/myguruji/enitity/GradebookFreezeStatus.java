package com.srdt.myguruji.enitity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "GradebookFreezeStatus.findFreezeStatus", query = "select FreezeStatus from GradebookFreezeStatus where batchDetails.BatchId=:batchid and courseDetails.CourseId=:courseid "),
        @NamedQuery(name = "GradebookFreezeStatus.findPushStatus", query = "select PushStatus from GradebookFreezeStatus where batchDetails.BatchId=:batchid and courseDetails.CourseId=:courseid "),
        @NamedQuery(name = "GradebookFreezeStatus.getGradebookFreezeStatus", query = "select fzs from GradebookFreezeStatus fzs where fzs.batchDetails.BatchId=:batchid and fzs.courseDetails.CourseId=:courseid and fzs.Term=:term "),
        @NamedQuery(name = "GradebookFreezeStatus.checkGradebookFreezeStatus", query = "select count(*) from GradebookFreezeStatus where batchDetails.BatchId=:batchid and courseDetails.CourseId=:courseid and Term=:term "),
        @NamedQuery(name="GradebookFreezeStatus.deleteGradebookByBatchId",query="delete from GradebookFreezeStatus where batchDetails.BatchId=:batchid "),
        @NamedQuery(name="GradebookFreezeStatus.updatePushStatus",query="update GradebookFreezeStatus set PushStatus='Y' where batchDetails.BatchId=:batchid"),
        @NamedQuery(name="GradebookFreezeStatus.getCountGradebookByBatchId",query="select count(*) from GradebookFreezeStatus where batchDetails.BatchId=:batchid ")
})
@Table(name = "GradebookFreezeStatus",uniqueConstraints = @UniqueConstraint(columnNames = {"BatchId","CourseId"}))

public class GradebookFreezeStatus extends SharedField  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(allocationSize = 1,initialValue=1,name = "GradebookFreezeStatus_Sqr",sequenceName="GradebookFreezeStatus_Sqr")
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="GradebookFreezeStatus_Sqr")
    @Column
    private long GradebookFreezeStatusId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BatchId",referencedColumnName="BatchId",nullable = false,insertable=true,updatable=true)
    @NotNull
    private BatchDetails batchDetails;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CourseId",referencedColumnName = "CourseId",insertable = true,updatable = true,nullable = false)
    @NotNull
    private CourseDetails courseDetails;
    @Column
    private String FreezeStatus;
    @Column
    private String PushStatus;
    @Column
    private String Term;

    public GradebookFreezeStatus() {    	
    }

    public String getPushStatus() {
		return PushStatus;
	}

	public void setPushStatus(String pushStatus) {
		PushStatus = pushStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public GradebookFreezeStatus(long gradebookFreezeStatusId, @NotNull BatchDetails batchDetails,
			@NotNull CourseDetails courseDetails, String freezeStatus, String pushStatus, String term) {
		super();
		GradebookFreezeStatusId = gradebookFreezeStatusId;
		this.batchDetails = batchDetails;
		this.courseDetails = courseDetails;
		FreezeStatus = freezeStatus;
		PushStatus = pushStatus;
		Term = term;
	}

	public GradebookFreezeStatus(long gradebookFreezeStatusId, @NotNull BatchDetails batchDetails, @NotNull CourseDetails courseDetails, String freezeStatus, String term) {
        GradebookFreezeStatusId = gradebookFreezeStatusId;
        this.batchDetails = batchDetails;
        this.courseDetails = courseDetails;
        FreezeStatus = freezeStatus;
        Term = term;
    }

    public long getGradebookFreezeStatusId() {
        return GradebookFreezeStatusId;
    }

    public void setGradebookFreezeStatusId(long gradebookFreezeStatusId) {
        GradebookFreezeStatusId = gradebookFreezeStatusId;
    }

    public BatchDetails getBatchDetails() {
        return batchDetails;
    }

    public void setBatchDetails(BatchDetails batchDetails) {
        this.batchDetails = batchDetails;
    }

    public CourseDetails getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(CourseDetails courseDetails) {
        this.courseDetails = courseDetails;
    }

    public String getFreezeStatus() {
        return FreezeStatus;
    }

    public void setFreezeStatus(String freezeStatus) {
        FreezeStatus = freezeStatus;
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
    }
}
