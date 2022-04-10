package com.srdt.myguruji.model;

import javax.persistence.Column;

public class FeedbackGradeParent {

    private long feedbackgradeparentid;
    private String feedbacktypename;
    private String feedbackparentpath;
    private String feedbackparent;
    private String createdby;

    public FeedbackGradeParent() {
    }

    public FeedbackGradeParent(long feedbackgradeparentid, String feedbacktypename, String feedbackparentpath, String feedbackparent, String createdby) {
        this.feedbackgradeparentid = feedbackgradeparentid;
        this.feedbacktypename = feedbacktypename;
        this.feedbackparentpath = feedbackparentpath;
        this.feedbackparent = feedbackparent;
        this.createdby = createdby;
    }

    public long getFeedbackgradeparentid() {
        return feedbackgradeparentid;
    }

    public void setFeedbackgradeparentid(long feedbackgradeparentid) {
        this.feedbackgradeparentid = feedbackgradeparentid;
    }

    public String getFeedbacktypename() {
        return feedbacktypename;
    }

    public void setFeedbacktypename(String feedbacktypename) {
        this.feedbacktypename = feedbacktypename;
    }

    public String getFeedbackparentpath() {
        return feedbackparentpath;
    }

    public void setFeedbackparentpath(String feedbackparentpath) {
        this.feedbackparentpath = feedbackparentpath;
    }

    public String getFeedbackparent() {
        return feedbackparent;
    }

    public void setFeedbackparent(String feedbackparent) {
        this.feedbackparent = feedbackparent;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
}