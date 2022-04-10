package com.srdt.myguruji.enitity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "GradeBookLamtypeMapping.deleteGradeBookLamtypeMappingByBatchId", query = "delete from GradeBookLamtypeMapping where batchDetails.BatchId=:batchid"),
        @NamedQuery(name = "GradeBookLamtypeMapping.getCountGradeBookLamtypeMappingByBatchId", query = "select count(*) from GradeBookLamtypeMapping where batchDetails.BatchId=:batchid")
})
        public class GradeBookLamtypeMapping  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(allocationSize = 1,initialValue=1,name = "GradeBookLamtypeMapping_Sqr",sequenceName="GradeBookLamtypeMapping_Sqr")
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="GradeBookLamtypeMapping_Sqr")
    @Column
    private long GradeBookLamtypeMappingId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BatchId",referencedColumnName="BatchId",nullable = false,insertable=true,updatable=true)
    @NotNull
    private BatchDetails batchDetails;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CourseId",referencedColumnName = "CourseId",insertable = true,updatable = true,nullable = false)
    @NotNull
    private CourseDetails courseDetails;

    @Column
    private String PsLamType;

    @Column
    private String LmsLamType;

    @Column
    private String Term;

    public GradeBookLamtypeMapping() {
    }

    public GradeBookLamtypeMapping(long gradeBookLamtypeMappingId, @NotNull BatchDetails batchDetails, @NotNull CourseDetails courseDetails, String psLamType, String lmsLamType, String term) {
        GradeBookLamtypeMappingId = gradeBookLamtypeMappingId;
        this.batchDetails = batchDetails;
        this.courseDetails = courseDetails;
        PsLamType = psLamType;
        LmsLamType = lmsLamType;
        Term = term;
    }

    public long getGradeBookLamtypeMappingId() {
        return GradeBookLamtypeMappingId;
    }

    public void setGradeBookLamtypeMappingId(long gradeBookLamtypeMappingId) {
        GradeBookLamtypeMappingId = gradeBookLamtypeMappingId;
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

    public String getPsLamType() {
        return PsLamType;
    }

    public void setPsLamType(String psLamType) {
        PsLamType = psLamType;
    }

    public String getLmsLamType() {
        return LmsLamType;
    }

    public void setLmsLamType(String lmsLamType) {
        LmsLamType = lmsLamType;
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
    }
}
