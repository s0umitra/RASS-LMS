package com.example.lms;

public class LeaveInfo {
    private String datefrom,dateto,subject,reason;
    private int id,status,eid;

    public int getEid(){
        return eid;
    }

    public void setEid(int eid){
        this.eid = eid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public String getDatefrom() {
        return datefrom;
    }

    public String getDateto() {
        return dateto;
    }

    public String getReason() {
        return reason;
    }

    public String getSubject() {
        return subject;
    }

    public void setDatefrom(String datefrom) {
        this.datefrom = datefrom;
    }

    public void setDateto(String dateto) {
        this.dateto = dateto;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
