package com.example.sayali.farmerauction.Model;

public class Time {

 String pid,bidwaladate,bidwalatime;

    public Time() {
    }

    public Time(String pid, String bidwaladate, String bidwalatime) {
        this.pid = pid;
        this.bidwaladate = bidwaladate;
        this.bidwalatime = bidwalatime;
    }


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBidwaladate() {
        return bidwaladate;
    }

    public void setBidwaladate(String bidwaladate) {
        this.bidwaladate = bidwaladate;
    }

    public String getBidwalatime() {
        return bidwalatime;
    }

    public void setBidwalatime(String bidwalatime) {
        this.bidwalatime = bidwalatime;
    }
}
