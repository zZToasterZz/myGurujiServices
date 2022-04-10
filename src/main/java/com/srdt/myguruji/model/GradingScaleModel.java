package com.srdt.myguruji.model;

public class GradingScaleModel {

    private long gradingscaleid;
    private String gradingname;
    private String gradingpoint;
    private String gradingpointvalue;
    private String createdby;

    public GradingScaleModel() {
    }

    public GradingScaleModel(long gradingscaleid, String gradingname, String gradingpoint, String gradingpointvalue, String createdby) {
        this.gradingscaleid = gradingscaleid;
        this.gradingname = gradingname;
        this.gradingpoint = gradingpoint;
        this.gradingpointvalue = gradingpointvalue;
        this.createdby = createdby;
    }

    public long getGradingscaleid() {
        return gradingscaleid;
    }

    public void setGradingscaleid(long gradingscaleid) {
        this.gradingscaleid = gradingscaleid;
    }

    public String getGradingname() {
        return gradingname;
    }

    public void setGradingname(String gradingname) {
        this.gradingname = gradingname;
    }

    public String getGradingpoint() {
        return gradingpoint;
    }

    public void setGradingpoint(String gradingpoint) {
        this.gradingpoint = gradingpoint;
    }

    public String getGradingpointvalue() {
        return gradingpointvalue;
    }

    public void setGradingpointvalue(String gradingpointvalue) {
        this.gradingpointvalue = gradingpointvalue;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
}
