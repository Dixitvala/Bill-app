package com.example.billapp;

public class ItemModel {
    String pname, pdesc, price, id;

    public ItemModel() {
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemModel(String pname, String pdesc, String price, String id) {
        this.pname = pname;
        this.pdesc = pdesc;
        this.price = price;
        this.id = id;
    }
}
