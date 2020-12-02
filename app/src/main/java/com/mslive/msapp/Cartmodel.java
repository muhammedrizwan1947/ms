package com.mslive.msapp;

import com.google.firebase.database.Exclude;

public class Cartmodel {
    String id,productid,size,quantity;


    public Cartmodel(){

    }

    public Cartmodel(String productid,String size, String quantity) {

        this.productid=productid;
        this.size = size;
        this.quantity = quantity;

    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }
}
