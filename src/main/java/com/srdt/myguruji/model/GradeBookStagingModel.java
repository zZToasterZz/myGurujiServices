package com.srdt.myguruji.model;

public class GradeBookStagingModel {
    public String gradebookstatgingid;
    public String batchsession;
    public String batchseq;
    public String classnumber;
    public String ssrcomponent;
    public String catalognbr;
    public String sequenceno;
    public String lamtype;
    public String descr;
    public String descrshort;
    public String marksouof;
    public String lamweight;
    public String term;

    public GradeBookStagingModel() {
    }

    public GradeBookStagingModel(String gradebookstatgingid, String batchsession, String batchseq, String classnumber, String ssrcomponent, String catalognbr, String sequenceno, String lamtype, String descr, String descrshort, String marksouof, String lamweight, String term) {
        this.gradebookstatgingid = gradebookstatgingid;
        this.batchsession = batchsession;
        this.batchseq = batchseq;
        this.classnumber = classnumber;
        this.ssrcomponent = ssrcomponent;
        this.catalognbr = catalognbr;
        this.sequenceno = sequenceno;
        this.lamtype = lamtype;
        this.descr = descr;
        this.descrshort = descrshort;
        this.marksouof = marksouof;
        this.lamweight = lamweight;
        this.term = term;
    }

    public String getGradebookstatgingid() {
        return gradebookstatgingid;
    }

    public void setGradebookstatgingid(String gradebookstatgingid) {
        this.gradebookstatgingid = gradebookstatgingid;
    }

    public String getBatchsession() {
        return batchsession;
    }

    public void setBatchsession(String batchsession) {
        this.batchsession = batchsession;
    }

    public String getBatchseq() {
        return batchseq;
    }

    public void setBatchseq(String batchseq) {
        this.batchseq = batchseq;
    }

    public String getClassnumber() {
        return classnumber;
    }

    public void setClassnumber(String classnumber) {
        this.classnumber = classnumber;
    }

    public String getSsrcomponent() {
        return ssrcomponent;
    }

    public void setSsrcomponent(String ssrcomponent) {
        this.ssrcomponent = ssrcomponent;
    }

    public String getCatalognbr() {
        return catalognbr;
    }

    public void setCatalognbr(String catalognbr) {
        this.catalognbr = catalognbr;
    }

    public String getSequenceno() {
        return sequenceno;
    }

    public void setSequenceno(String sequenceno) {
        this.sequenceno = sequenceno;
    }

    public String getLamtype() {
        return lamtype;
    }

    public void setLamtype(String lamtype) {
        this.lamtype = lamtype;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDescrshort() {
        return descrshort;
    }

    public void setDescrshort(String descrshort) {
        this.descrshort = descrshort;
    }

    public String getMarksouof() {
        return marksouof;
    }

    public void setMarksouof(String marksouof) {
        this.marksouof = marksouof;
    }

    public String getLamweight() {
        return lamweight;
    }

    public void setLamweight(String lamweight) {
        this.lamweight = lamweight;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
