package com.mslive.msapp;

import com.google.firebase.database.Exclude;

public class ProductModel {
    String mId,mProductname,mProductdescription,mProductnumber,mProductprice,mImageurl,mSmallsize,mMediumsize,mLargesize,mWeartype,mMaterial;
    private String mKey;

    public ProductModel(){

    }

    public ProductModel(String mId, String mProductname, String mProductdescription, String mProductnumber, String mProductprice, String mImageurl, String mSmallsize, String mMediumsize, String mLargesize, String mWeartype,String mMaterial) {
        this.mId = mId;
        this.mProductname = mProductname;
        this.mProductdescription = mProductdescription;
        this.mProductnumber = mProductnumber;
        this.mProductprice = mProductprice;
        this.mImageurl = mImageurl;
        this.mSmallsize = mSmallsize;
        this.mMediumsize = mMediumsize;
        this.mLargesize = mLargesize;
        this.mWeartype = mWeartype;
        this.mMaterial=mMaterial;
    }


    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmProductname() {
        return mProductname;
    }

    public void setmProductname(String mProductname) {
        this.mProductname = mProductname;
    }

    public String getmProductdescription() {
        return mProductdescription;
    }

    public void setmProductdescription(String mProductdescription) {
        this.mProductdescription = mProductdescription;
    }

    public String getmProductnumber() {
        return mProductnumber;
    }

    public void setmProductnumber(String mProductnumber) {
        this.mProductnumber = mProductnumber;
    }

    public String getmProductprice() {
        return mProductprice;
    }

    public void setmProductprice(String mProductprice) {
        this.mProductprice = mProductprice;
    }

    public String getmImageurl() {
        return mImageurl;
    }

    public void setmImageurl(String mImageurl) {
        this.mImageurl = mImageurl;
    }

    public String getmSmallsize() {
        return mSmallsize;
    }

    public void setmSmallsize(String mSmallsize) {
        this.mSmallsize = mSmallsize;
    }

    public String getmMediumsize() {
        return mMediumsize;
    }

    public void setmMediumsize(String mMediumsize) {
        this.mMediumsize = mMediumsize;
    }

    public String getmLargesize() {
        return mLargesize;
    }

    public void setmLargesize(String mLargesize) {
        this.mLargesize = mLargesize;
    }

    public String getmWeartype() {
        return mWeartype;
    }

    public void setmWeartype(String mWeartype) {
        this.mWeartype = mWeartype;
    }

    public String getmMaterial() {
        return mMaterial;
    }

    public void setmMaterial(String mMaterial) {
        this.mMaterial = mMaterial;
    }

    @Exclude
    public String getKey(){return mKey;}


    @Exclude
    public void setKey(String key){
        mKey = key;
    }


}
