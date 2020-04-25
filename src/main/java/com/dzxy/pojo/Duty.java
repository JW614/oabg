package com.dzxy.pojo;

import java.sql.Date;

public class Duty {
    private int dtid;  //考勤编号
    private Date dtdate; //签到日期  java.sql.Date  只获取yyyy-MM-dd
    private String signintime;//签到时间
    private String signouttime;//签退时间
    private String incity;//签到城市
    private String outcity;//签退城市
    private String empId;//员工编号
    private Employee emp;//员工信息

    public Duty() {

    }

    public Duty(Date dtdate, String signintime, String signouttime, String empId) {
        super();
        this.dtdate = dtdate;
        this.signintime = signintime;
        this.signouttime = signouttime;
        this.empId = empId;
    }

    public Duty(int dtid, Date dtdate, String signintime, String signouttime,
                Employee emp) {
        super();
        this.dtid = dtid;
        this.dtdate = dtdate;
        this.signintime = signintime;
        this.signouttime = signouttime;
        this.emp = emp;
    }

    public int getDtid() {
        return dtid;
    }

    public void setDtid(int dtid) {
        this.dtid = dtid;
    }

    public Date getDtdate() {
        return dtdate;
    }

    public void setDtdate(Date dtdate) {
        this.dtdate = dtdate;
    }

    public String getSignintime() {
        return signintime;
    }

    public void setSignintime(String signintime) {
        this.signintime = signintime;
    }

    public String getSignouttime() {
        return signouttime;
    }

    public void setSignouttime(String signouttime) {
        this.signouttime = signouttime;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public String getIncity() {
        return incity;
    }

    public void setIncity(String incity) {
        this.incity = incity;
    }

    public String getOutcity() {
        return outcity;
    }

    public void setOutcity(String outcity) {
        this.outcity = outcity;
    }
}
