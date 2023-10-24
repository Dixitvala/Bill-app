package com.example.billapp;

public class CustVendModel {
    String cname, cno, email, address, note;

    public CustVendModel() {
    }

    public CustVendModel(String cname, String cno, String email, String address, String note) {
        this.cname = cname;
        this.cno = cno;
        this.email = email;
        this.address = address;
        this.note = note;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
