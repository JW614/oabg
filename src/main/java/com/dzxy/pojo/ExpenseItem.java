package com.dzxy.pojo;

/**
 * 报销单明细
 *
 * @author Administrator
 */
public class ExpenseItem {
    private int itemId;//明细编号  自增
    private String type;// 明细类型
    private double amoumt;//明细的金额
    private String itemDesc;//明细的描述

    private int expId;//所属报销单的编号
    private Expense exp;//所属报销单

    public ExpenseItem() {

    }

    public ExpenseItem(String type, double amoumt, String itemDesc, int expId) {
        super();
        this.type = type;
        this.amoumt = amoumt;
        this.itemDesc = itemDesc;
        this.expId = expId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmoumt() {
        return amoumt;
    }

    public void setAmoumt(double amoumt) {
        this.amoumt = amoumt;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public Expense getExp() {
        return exp;
    }

    public void setExp(Expense exp) {
        this.exp = exp;
    }

}
