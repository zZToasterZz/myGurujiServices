package com.srdt.myguruji.enitity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GradeBookStaging")
@NamedQueries({
        @NamedQuery(name = "GradeBookStaging.deleteGradeBookStagingByClassNumber", query = "delete from GradeBookStaging where ClassNumber=:classnbr"),
        @NamedQuery(name = "GradeBookStaging.getCountGradeBookStagingByClassNumber", query = "select count(*) from GradeBookStaging where ClassNumber=:classnbr")
})
        public class GradeBookStaging extends SharedField implements Serializable  {

    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "GradeBookStaging_Sqr", sequenceName = "GradeBookStaging_Sqr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GradeBookStaging_Sqr")
    private long GradeBookStagingId;

    @Column(length = 30)
    public String BatchSession;
    @Column(length = 30)
    public String BatchSeq;
    @Column(length = 30)
    public String ClassNumber;
    @Column(length = 30)
    public String SsrComponent;
    @Column(length = 30)
    public String CatalogNbr;
    @Column
    public String SequenceNo;
    @Column(length = 30)
    public String LamType;
    @Column(length = 300)
    public String Descr;
    @Column(length = 30)
    public String DescrShort;
    @Column
    public String MarksOuOf;
    @Column
    public String LamWeight;
    @Column
    public String Term;

    public GradeBookStaging() {
    }

    public GradeBookStaging(long gradeBookStagingId, String batchSession, String batchSeq, String classNumber, String ssrComponent, String catalogNbr, String sequenceNo, String lamType, String descr, String descrShort, String marksOuOf, String lamWeight, String term) {
        GradeBookStagingId = gradeBookStagingId;
        BatchSession = batchSession;
        BatchSeq = batchSeq;
        ClassNumber = classNumber;
        SsrComponent = ssrComponent;
        CatalogNbr = catalogNbr;
        SequenceNo = sequenceNo;
        LamType = lamType;
        Descr = descr;
        DescrShort = descrShort;
        MarksOuOf = marksOuOf;
        LamWeight = lamWeight;
        Term = term;
        setModifiedBy("SRKLEM0028");
        setCreatedBy("SRKLEM0028");
    }
/*
    public GradeBookStaging(long gradeBookStagingId, String classNumber, String sequenceNo, String term) {
        GradeBookStagingId = gradeBookStagingId;
        ClassNumber = classNumber;
        SequenceNo = sequenceNo;
        Term = term;

    }*/


    public long getGradeBookStagingId() {
        return GradeBookStagingId;
    }

    public void setGradeBookStagingId(long gradeBookStagingId) {
        GradeBookStagingId = gradeBookStagingId;
    }

    public String getBatchSession() {
        return BatchSession;
    }

    public void setBatchSession(String batchSession) {
        BatchSession = batchSession;
    }

    public String getBatchSeq() {
        return BatchSeq;
    }

    public void setBatchSeq(String batchSeq) {
        BatchSeq = batchSeq;
    }

    public String getClassNumber() {
        return ClassNumber;
    }

    public void setClassNumber(String classNumber) {
        ClassNumber = classNumber;
    }

    public String getSsrComponent() {
        return SsrComponent;
    }

    public void setSsrComponent(String ssrComponent) {
        SsrComponent = ssrComponent;
    }

    public String getCatalogNbr() {
        return CatalogNbr;
    }

    public void setCatalogNbr(String catalogNbr) {
        CatalogNbr = catalogNbr;
    }

    public String getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        SequenceNo = sequenceNo;
    }

    public String getLamType() {
        return LamType;
    }

    public void setLamType(String lamType) {
        LamType = lamType;
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

    public String getMarksOuOf() {
        return MarksOuOf;
    }

    public void setMarksOuOf(String marksOuOf) {
        MarksOuOf = marksOuOf;
    }

    public String getLamWeight() {
        return LamWeight;
    }

    public void setLamWeight(String lamWeight) {
        LamWeight = lamWeight;
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
    }
}