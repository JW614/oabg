package com.dzxy.pojo;

import java.util.Date;

/**
 * 支出类
 *
 * @author Administrator
 */
public class Payment {
    private int pid;//支出的编号
    private double amount;//支出金额
    private Date paytime;//支出的时间
    private int expId;//产生支出的报销单编号
    private String expEmpId;//报销人的编号
    private String auditEmpId;//审核人的编号
    private Employee emp;
    private ExpenseItem expItem;
//	private Employee expEmp;
//	private Employee auditEmp;

    public Payment() {

    }

    public Payment(double amount, Date paytime, int expId,
                   Employee emp, ExpenseItem expItem) {
        super();
        this.amount = amount;
        this.paytime = paytime;
        this.expId = expId;
        this.emp = emp;
        this.expItem = expItem;
    }

    public Payment(double amount, Date paytime, int expId, String expEmpId,
                   String auditEmpId) {
        super();
        this.amount = amount;
        this.paytime = paytime;
        this.expId = expId;
        this.expEmpId = expEmpId;
        this.auditEmpId = auditEmpId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public String getExpEmpId() {
        return expEmpId;
    }

    public void setExpEmpId(String expEmpId) {
        this.expEmpId = expEmpId;
    }

    public String getAuditEmpId() {
        return auditEmpId;
    }

    public void setAuditEmpId(String auditEmpId) {
        this.auditEmpId = auditEmpId;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public ExpenseItem getExpItem() {
        return expItem;
    }

    public void setExpItem(ExpenseItem expItem) {
        this.expItem = expItem;
    }

    @Override
    public String toString() {
        return "Payment [pid=" + pid + ", amount=" + amount + ", paytime="
                + paytime + ", expId=" + expId + ", expEmpId=" + expEmpId
                + ", auditEmpId=" + auditEmpId + "]";
    }


}
