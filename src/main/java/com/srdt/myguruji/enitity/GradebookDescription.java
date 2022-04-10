package com.srdt.myguruji.enitity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
    @NamedQuery(name="GradebookDescription.deleteGradebookDescriptionByBatchId",query="delete from GradebookDescription where batchDetails.BatchId=:batchid"),
    @NamedQuery(name="GradebookDescription.getCountGradebookDescriptionByBatchId",query="select count(*) from GradebookDescription where batchDetails.BatchId=:batchid"),

})
public class GradebookDescription extends SharedField implements Serializable {

    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "GradeBookDescr_Sqr", sequenceName = "GradeBookDescr_Sqr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GradeBookDescr_Sqr")
    private long GradbookDescrId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BatchId",referencedColumnName="BatchId",nullable = false,insertable=true,updatable=true)
    private BatchDetails batchDetails;

    @Column
    private String Descr;

    @Column
    private String DescrShort;

    @Column
    private String LamType;

    @Column
    private String LamWeight;

    @Column
    private double MarksOutOf;

    @Column
    private String SequenceNo;

    @Column
    private String Term;

    public GradebookDescription() {
    }

    public GradebookDescription(long gradbookDescrId, BatchDetails batchDetails, String descr, String descrShort, String lamType, String lamWeight, double marksOutOf, String sequenceNo, String term) {
        GradbookDescrId = gradbookDescrId;
        this.batchDetails = batchDetails;
        Descr = descr;
        DescrShort = descrShort;
        LamType = lamType;
        LamWeight = lamWeight;
        MarksOutOf = marksOutOf;
        SequenceNo = sequenceNo;
        Term = term;
        setCreatedBy("SRKLEM0028");
        setModifiedBy("SRKLEM0028");
    }

    public long getGradbookDescrId() {
        return GradbookDescrId;
    }

    public void setGradbookDescrId(long gradbookDescrId) {
        GradbookDescrId = gradbookDescrId;
    }

    public BatchDetails getBatchDetails() {
        return batchDetails;
    }

    public void setBatchDetails(BatchDetails batchDetails) {
        this.batchDetails = batchDetails;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public String getDescrShort() {
        return DescrShort;
    }

    public void setDescrShort(String descrShort) {
        DescrShort = descrShort;
    }

    public String getLamType() {
        return LamType;
    }

    public void setLamType(String lamType) {
        LamType = lamType;
    }

    public String getLamWeight() {
        return LamWeight;
    }

    public void setLamWeight(String lamWeight) {
        LamWeight = lamWeight;
    }

    public double getMarksOutOf() {
        return MarksOutOf;
    }

    public void setMarksOutOf(double marksOutOf) {
        MarksOutOf = marksOutOf;
    }

    public String getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        SequenceNo = sequenceNo;
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
    }
}
