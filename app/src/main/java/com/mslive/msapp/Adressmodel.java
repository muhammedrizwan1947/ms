package com.mslive.msapp;

public class Adressmodel {
    String id,name,address,state,phone,pincode;


    public Adressmodel(){

    }


    public Adressmodel(String id, String name, String address, String state, String phone, String pincode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.state = state;
        this.phone = phone;
        this.pincode = pincode;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
