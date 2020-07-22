package com.example.sayali.farmerauction.Model;

public class CustomerProduct {

    public String bidendtime, bidenddate,date,image,name,pid,quantity,time,value,phone,username,useradd;

    public CustomerProduct() {
    }

    public CustomerProduct(String bidendtime, String phone,String username,String useradd,String bidenddate, String date, String image, String name, String pid, String quantity, String time, String value) {
        this.bidendtime = bidendtime;
        this.phone=phone;
        this.date = date;
        this.image = image;
        this.name = name;
        this.pid = pid;
        this.quantity = quantity;
        this.time = time;
        this.value = value;
        this.bidenddate=bidenddate;
        this.username=username;
        this.useradd=useradd;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseradd() {
        return useradd;
    }

    public void setUseradd(String useradd) {
        this.useradd = useradd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBidenddate() {
        return bidenddate;
    }

    public void setBidenddate(String bidenddate) {
        this.bidenddate = bidenddate;
    }

    public String getBidendtime() {
        return bidendtime;
    }

    public void setBidendtime(String bidendtime) {
        this.bidendtime = bidendtime;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

