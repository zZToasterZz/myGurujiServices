package com.srdt.myguruji.model;

public class GradebookMarksModel {
    private double marks;
    private String emplid;

    public GradebookMarksModel() {
    }

    public GradebookMarksModel(double marks, String emplid) {
        this.marks = marks;
        this.emplid = emplid;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }
}
