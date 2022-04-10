package com.srdt.myguruji.model;

public class FeedbackGradingValue {

    private String gradingname;
    private String lowestgradevalue;
    private String lowergradevalue;
    private String avggradevalue;
    private String highergradevalue;
    private String highestgradevalue;

    public FeedbackGradingValue() {
    }

    public FeedbackGradingValue(String gradingname, String lowestgradevalue, String lowergradevalue, String avggradevalue, String highergradevalue, String highestgradevalue) {
        this.gradingname = gradingname;
        this.lowestgradevalue = lowestgradevalue;
        this.lowergradevalue = lowergradevalue;
        this.avggradevalue = avggradevalue;
        this.highergradevalue = highergradevalue;
        this.highestgradevalue = highestgradevalue;
    }

    public String getGradingname() {
        return gradingname;
    }

    public void setGradingname(String gradingname) {
        this.gradingname = gradingname;
    }

    public String getLowestgradevalue() {
        return lowestgradevalue;
    }

    public void setLowestgradevalue(String lowestgradevalue) {
        this.lowestgradevalue = lowestgradevalue;
    }

    public String getLowergradevalue() {
        return lowergradevalue;
    }

    public void setLowergradevalue(String lowergradevalue) {
        this.lowergradevalue = lowergradevalue;
    }

    public String getAvggradevalue() {
        return avggradevalue;
    }

    public void setAvggradevalue(String avggradevalue) {
        this.avggradevalue = avggradevalue;
    }

    public String getHighergradevalue() {
        return highergradevalue;
    }

    public void setHighergradevalue(String highergradevalue) {
        this.highergradevalue = highergradevalue;
    }

    public String getHighestgradevalue() {
        return highestgradevalue;
    }

    public void setHighestgradevalue(String highestgradevalue) {
        this.highestgradevalue = highestgradevalue;
    }
}
