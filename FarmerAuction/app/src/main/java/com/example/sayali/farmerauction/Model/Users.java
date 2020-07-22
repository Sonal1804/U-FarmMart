package com.example.sayali.farmerauction.Model;

public class Users {
    private String image, name, phone, address, pass,phoneOrder;

    public Users() {
    }

    public Users(String image,String phoneOrder ,String name, String phone, String address, String pass) {
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.pass = pass;
        this.phoneOrder=phoneOrder;
    }

    public String getPhoneOrder() {
        return phoneOrder;
    }

    public void setPhoneOrder(String phoneOrder) {
        this.phoneOrder = phoneOrder;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}