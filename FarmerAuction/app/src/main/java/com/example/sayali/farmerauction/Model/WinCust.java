package com.example.sayali.farmerauction.Model;

public class WinCust {
    public String pname,farmname,city,winname,winprice;

    public WinCust() {
    }

    public WinCust(String pname, String farmname, String city, String winname, String winprice) {
        this.pname = pname;
        this.farmname = farmname;
        this.city = city;
        this.winname = winname;
        this.winprice = winprice;
    }


    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getFarmname() {
        return farmname;
    }

    public void setFarmname(String farmname) {
        this.farmname = farmname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWinname() {
        return winname;
    }

    public void setWinname(String winname) {
        this.winname = winname;
    }

    public String getWinprice() {
        return winprice;
    }

    public void setWinprice(String winprice) {
        this.winprice = winprice;
    }
}
