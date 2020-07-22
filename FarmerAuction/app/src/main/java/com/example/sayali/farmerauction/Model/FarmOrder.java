package com.example.sayali.farmerauction.Model;

public class FarmOrder {

    String pid,pname,price,userphone,saveCurrentdt,name,address;

    public FarmOrder() {
    }

    public FarmOrder(String pid, String name,String address,String pname,String saveCurrentdt, String price,String userphone) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.userphone=userphone;
        this.saveCurrentdt=saveCurrentdt;
        this.name=name;
        this.address=address;

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

    public String getSaveCurrentdt() {
        return saveCurrentdt;
    }

    public void setSaveCurrentdt(String saveCurrentdt) {
        this.saveCurrentdt = saveCurrentdt;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
