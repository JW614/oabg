package com.dzxy.pojo;

import java.util.Date;

/**
 * 审核记录
 *
 * @author Administrator
 */
public class Auditing {
    private int auditId;//审核编号  自增
    private String result;//审核结果   通过  拒绝  打回
    private String auditDesc;//审核意见
    private Date auditTime;//
    private int expId;//审核的报销单的编号
    private String auditorId;//审核人的编号
    private Employee auditor;//审核人
    private Expense exp;//审核的报销单

    public Auditing() {

    }

    public Auditing(String result, String auditDesc, Date auditTime, int expId,
                    Employee auditor) {
        super();
        this.result = result;
        this.auditDesc = auditDesc;
        this.auditTime = auditTime;
        this.expId = expId;
        this.auditor = auditor;
    }

    public Auditing(String result, String auditDesc, int expId, String auditorId) {
        super();
        this.result = result;
        this.auditDesc = auditDesc;
        this.expId = expId;
        this.auditorId = auditorId;
    }

    public Auditing(String result, String auditDesc, Date auditTime, int expId,
                    String auditorId) {
        super();
        this.result = result;
        this.auditDesc = auditDesc;
        this.auditTime = auditTime;
        this.expId = expId;
        this.auditorId = auditorId;
    }

    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public Employee getAuditor() {
        return auditor;
    }

    public void setAuditor(Employee auditor) {
        this.auditor = auditor;
    }

    public Expense getExp() {
        return exp;
    }

    public void setExp(Expense exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "Auditing [auditId=" + auditId + ", result=" + result
                + ", auditDesc=" + auditDesc + ", auditTime=" + auditTime
                + ", expId=" + expId + ", auditorId=" + auditorId
                + ", auditor=" + auditor + ", exp=" + exp + "]";
    }

}
