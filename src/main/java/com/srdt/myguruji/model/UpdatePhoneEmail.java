package com.srdt.myguruji.model;

public class UpdatePhoneEmail {
    private String emailid;
    private String emplid;
    private String contact;

    public UpdatePhoneEmail() {
    }

    public UpdatePhoneEmail(String emailid, String emplid, String contact) {
        this.emailid = emailid;
        this.emplid = emplid;
        this.contact = contact;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getEmplid() {
        return emplid;
    }

    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
