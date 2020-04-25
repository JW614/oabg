package com.dzxy.pojo;

import java.util.Date;

public class InCome {
    private int icId;//收入单编号  自增
    private double amount;//收入金额
    private Date idDate;//产生收入的日期
    private String icType;//产生收入的类型
    private String inDesc;//产生收入的说明
    private String userId;//处理收入的人员编号

    private Employee emp;

    public InCome() {

    }

    public InCome(double amount, Date idDate, String icType, String inDesc,
                  String userId) {
        super();
        this.amount = amount;
        this.idDate = idDate;
        this.icType = icType;
        this.inDesc = inDesc;
        this.userId = userId;
    }

    public InCome(int icId, double amount, Date idDate, String icType, String inDesc,
                  Employee emp) {
        super();
        this.icId = icId;
        this.amount = amount;
        this.idDate = idDate;
        this.icType = icType;
        this.inDesc = inDesc;
        this.emp = emp;
    }

    public int getIcId() {
        return icId;
    }

    public void setIcId(int icId) {
        this.icId = icId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getIdDate() {
        return idDate;
    }

    public void setIdDate(Date idDate) {
        this.idDate = idDate;
    }

    public String getIcType() {
        return icType;
    }

    public void setIcType(String icType) {
        this.icType = icType;
    }

    public String getInDesc() {
        return inDesc;
    }

    public void setInDesc(String inDesc) {
        this.inDesc = inDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    @Override
    public String toString() {
        return "InCome [icId=" + icId + ", amount=" + amount + ", idDate="
                + idDate + ", icType=" + icType + ", inDesc=" + inDesc
                + ", userId=" + userId + ", emp=" + emp + "]";
    }

}
