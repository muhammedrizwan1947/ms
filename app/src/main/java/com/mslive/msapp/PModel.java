package com.mslive.msapp;

import com.google.firebase.database.Exclude;

public class PModel {
    String id,productname,productdescription,productnumber,productprice,imageurl,smallsize,mediumsize,largesize,weartype,material;
    private String mKey;

    public PModel(){

    }

    public PModel(String id, String productname, String productdescription, String productnumber, String productprice, String imageurl, String smallsize, String mediumsize, String largesize, String weartype, String material) {
        this.id = id;
        this.productname = productname;
        this.productdescription = productdescription;
        this.productnumber = productnumber;
        this.productprice = productprice;
        this.imageurl = imageurl;
        this.smallsize = smallsize;
        this.mediumsize = mediumsize;
        this.largesize = largesize;
        this.weartype = weartype;
        this.material=material;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getProductnumber() {
        return productnumber;
    }

    public void setProductnumber(String productnumber) {
        this.productnumber = productnumber;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getSmallsize() {
        return smallsize;
    }

    public void setSmallsize(String smallsize) {
        this.smallsize = smallsize;
    }

    public String getMediumsize() {
        return mediumsize;
    }

    public void setMediumsize(String mediumsize) {
        this.mediumsize = mediumsize;
    }

    public String getLargesize() {
        return largesize;
    }

    public void setLargesize(String largesize) {
        this.largesize = largesize;
    }

    public String getWeartype() {
        return weartype;
    }

    public void setWeartype(String weartype) {
        this.weartype = weartype;
    }


    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Exclude
    public String getKey(){return mKey;}


    @Exclude
    public void setKey(String key){
        mKey = key;
    }


}
