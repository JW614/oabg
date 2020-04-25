package com.dzxy.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报销单
 *
 * @author Administrator
 */
@ToString
@Getter
@Setter
public class Expense {
    private int expId;//报销单编号 自增
    private double totalAmount;//报销单总金额
    private Date expTime;//报销时间
    private String expDesc;//报销单描述

    private String lastResult;//最新的审核结果
    private String status;//报销单状态   0--未审核  1--审核中  2--审核结果通过  3--审核拒绝 4--审核打回 5--已打款

    private String empId;//报销人编号
    private Employee emp;//报销人

    private String nextAuditorId;//下一个审核人编号
    private Employee nextAuditor;//下一个审核人

    private Auditing auditing;
    private List<ExpenseImg> photoList;
    private List<ExpenseItem> itemList = new ArrayList<ExpenseItem>();//一个报销单可以有多个明细

    public Expense() {

    }

    public Expense(int expId, double totalAmount, Date expTime, String expDesc,
                   Employee emp, Auditing auditing) {
        super();
        this.expId = expId;
        this.totalAmount = totalAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
        this.emp = emp;
        this.auditing = auditing;
    }

    public Expense(int expId, double totalAmount, Date expTime, String expDesc,
                   String lastResult, String status, Employee emp) {
        super();
        this.expId = expId;
        this.totalAmount = totalAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
        this.lastResult = lastResult;
        this.status = status;
        this.emp = emp;
    }

    public Expense(double totalAmount, Date expTime, String expDesc,
                   String lastResult, String status, String empId,
                   String nextAuditorId, List<ExpenseItem> itemList,List<ExpenseImg> photoList) {
        super();
        this.totalAmount = totalAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
        this.lastResult = lastResult;
        this.status = status;
        this.empId = empId;
        this.nextAuditorId = nextAuditorId;
        this.itemList = itemList;
        this.photoList = photoList;
    }

    public Expense(double totalAmount, Date expTime, String expDesc,
                   String lastResult, String status, Employee emp,
                   Employee nextAuditor, List<ExpenseItem> itemList) {
        super();
        this.totalAmount = totalAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
        this.lastResult = lastResult;
        this.status = status;
        this.emp = emp;
        this.nextAuditor = nextAuditor;
        this.itemList = itemList;
    }

    public Expense(int expId, double totalAmount, Date expTime, String expDesc,
                   String lastResult, String status, Employee emp,
                   Employee nextAuditor, List<ExpenseItem> itemList) {
        super();
        this.expId = expId;
        this.totalAmount = totalAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
        this.lastResult = lastResult;
        this.status = status;
        this.emp = emp;
        this.nextAuditor = nextAuditor;
        this.itemList = itemList;
    }

    public Expense(int expId, double totalAmount, Date expTime, String expDesc,
                   String lastResult, String status, String empId, Employee emp,
                   String nextAuditorId, Employee nextAuditor,
                   List<ExpenseItem> itemList) {
        super();
        this.expId = expId;
        this.totalAmount = totalAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
        this.lastResult = lastResult;
        this.status = status;
        this.empId = empId;
        this.emp = emp;
        this.nextAuditorId = nextAuditorId;
        this.nextAuditor = nextAuditor;
        this.itemList = itemList;
    }
}
