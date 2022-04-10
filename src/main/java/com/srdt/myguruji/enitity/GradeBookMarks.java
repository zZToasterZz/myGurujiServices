package com.srdt.myguruji.enitity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name="GradeBookMarks.checkExisting",query="select count(*) from GradeBookMarks where batchDetails.BatchId=:batchid and courseDetails.CourseId=:courseid and PsLamType=:pslamtype and studentDetails.Emplid=:emplid"),
        @NamedQuery(name="GradeBookMarks.getGradebookMarks",query="select gm from GradeBookMarks gm where gm.batchDetails.BatchId=:batchid and gm.courseDetails.CourseId=:courseid and gm.PsLamType=:pslamtype and gm.studentDetails.Emplid=:emplid"),
        @NamedQuery(name="GradeBookMarks.getGradebookMarksbyBatchid",query="select gm from GradeBookMarks gm where gm.batchDetails.BatchId=:batchid and gm.courseDetails.CourseId=:courseid"),
        @NamedQuery(name="GradeBookMarks.deleteGradeBookMarksByBatchId",query="delete from GradeBookMarks where batchDetails.BatchId=:batchid"),
        @NamedQuery(name="GradeBookMarks.getCountGradeBookMarksByBatchId",query="select count(*) from GradeBookMarks where batchDetails.BatchId=:batchid")

})
public class GradeBookMarks implements Serializable {

    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "GradeBook_Sqr", sequenceName = "GradeBook_Sqr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GradeBook_Sqr")
    private long gradbookid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BatchId",referencedColumnName="BatchId",nullable = false,insertable=true,updatable=true)
    private BatchDetails batchDetails;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CourseId",referencedColumnName = "CourseId",insertable = true,updatable = true,nullable = false)
    private CourseDetails courseDetails;

    @Column(length = 30)
    private String PsLamType;
    @Column(length = 30)
    private String Assid;
    @Column(length = 30)
    private String AssType;
    @Column
    private double Marks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentId", referencedColumnName = "StudentId", insertable = true, updatable = true)
    private StudentDetails studentDetails;

    private String Term;

    public GradeBookMarks() {
    }

    public GradeBookMarks(long gradbookid, BatchDetails batchDetails, CourseDetails courseDetails, String psLamType, String assid, String assType, double marks, StudentDetails studentDetails, String term) {
        this.gradbookid = gradbookid;
        this.batchDetails = batchDetails;
        this.courseDetails = courseDetails;
        PsLamType = psLamType;
        Assid = assid;
        AssType = assType;
        Marks = marks;
        this.studentDetails = studentDetails;
        Term = term;
    }

    public long getGradbookid() {
        return gradbookid;
    }

    public void setGradbookid(long gradbookid) {
        this.gradbookid = gradbookid;
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

    public StudentDetails getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(StudentDetails studentDetails) {
        this.studentDetails = studentDetails;
    }

    public String getPsLamType() {
        return PsLamType;
    }

    public void setPsLamType(String psLamType) {
        PsLamType = psLamType;
    }

    public String getAssid() {
        return Assid;
    }

    public void setAssid(String assid) {
        Assid = assid;
    }

    public String getAssType() {
        return AssType;
    }

    public void setAssType(String assType) {
        AssType = assType;
    }

    public double getMarks() {
        return Marks;
    }

    public void setMarks(double marks) {
        Marks = marks;
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
    }
}
