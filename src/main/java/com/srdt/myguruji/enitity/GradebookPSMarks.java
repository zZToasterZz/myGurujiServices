package com.srdt.myguruji.enitity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name="GradebookPSMarks.getAllGradebookPSMarks",query="select gb from GradebookPSMarks gb"),
        @NamedQuery(name="GradebookPSMarks.deleteGradebookPSMarksByClassNumber",query="delete from GradebookPSMarks where ClassNumber=:classnbr"),
        @NamedQuery(name="GradebookPSMarks.getCountGradebookPSMarksByClassNumber",query="select count(*) from GradebookPSMarks where ClassNumber=:classnbr")
})

public class GradebookPSMarks implements Serializable {

    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "GradebookPSMarks_Sqr", sequenceName = "GradebookPSMarks_Sqr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GradebookPSMarks_Sqr")
    private long GradebookPSMarksId;

    @Column
    private String EmplId;

    @Column
    private String ClassNumber;

    @Column
    private String Strm;

    @Column
    private String Lamtype;

    @Column
    private String DescrShort;

    @Column
    private String SequenceNo;

    @Column
    private String MarksOutOf;

    @Column
    private String StudenrGrade;

    @Column
    private String InstructorId;

    public GradebookPSMarks() {
    }

    public GradebookPSMarks(long gradebookPSMarksId, String emplId, String classNumber, String strm, String lamtype, String descrShort, String sequenceNo, String marksOutOf, String studenrGrade, String instructorId) {
        GradebookPSMarksId = gradebookPSMarksId;
        EmplId = emplId;
        ClassNumber = classNumber;
        Strm = strm;
        Lamtype = lamtype;
        DescrShort = descrShort;
        SequenceNo = sequenceNo;
        MarksOutOf = marksOutOf;
        StudenrGrade = studenrGrade;
        InstructorId = instructorId;
    }

    public long getGradebookPSMarksId() {
        return GradebookPSMarksId;
    }

    public void setGradebookPSMarksId(long gradebookPSMarksId) {
        GradebookPSMarksId = gradebookPSMarksId;
    }

    public String getEmplId() {
        return EmplId;
    }

    public void setEmplId(String emplId) {
        EmplId = emplId;
    }

    public String getClassNumber() {
        return ClassNumber;
    }

    public void setClassNumber(String classNumber) {
        ClassNumber = classNumber;
    }

    public String getStrm() {
        return Strm;
    }

    public void setStrm(String strm) {
        Strm = strm;
    }

    public String getLamtype() {
        return Lamtype;
    }

    public void setLamtype(String lamtype) {
        Lamtype = lamtype;
    }

    public String getDescrShort() {
        return DescrShort;
    }

    public void setDescrShort(String descrShort) {
        DescrShort = descrShort;
    }

    public String getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        SequenceNo = sequenceNo;
    }

    public String getMarksOutOf() {
        return MarksOutOf;
    }

    public void setMarksOutOf(String marksOutOf) {
        MarksOutOf = marksOutOf;
    }

    public String getStudenrGrade() {
        return StudenrGrade;
    }

    public void setStudenrGrade(String studenrGrade) {
        StudenrGrade = studenrGrade;
    }

    public String getInstructorId() {
        return InstructorId;
    }

    public void setInstructorId(String instructorId) {
        InstructorId = instructorId;
    }
}
