package com.dzxy.service;

import com.dzxy.pojo.Auditing;
import com.dzxy.pojo.Expense;
import com.dzxy.pojo.ExpenseImg;
import com.dzxy.pojo.ExpenseItem;

import java.util.List;

public interface ExpenseService {
    /**
     * 添加报销单
     *
     * @param exp
     */
    void add(Expense exp);

    /**
     * 获取指定管理员的待审报销单
     *
     * @param empId
     * @param num
     * @param num2
     * @return
     */
    List<Expense> toAudit(String empId, int num, int num2);

    /**
     * 审核报销单
     *
     * @param auditing
     */
    void audit(Auditing auditing);

    /**
     * 查询指定报销单编号的明细信息
     *
     * @param expId
     * @return
     */
    List<ExpenseItem> see(int expId);

    /**
     * 查询指定报销单编号的审核信息
     *
     * @param expId
     * @return
     */
    List<Auditing> check(int expId);

    /**
     * 查询正在登录人的报销单信息
     *
     * @param empid
     * @param num
     * @param num2
     * @return
     */
    List<Expense> findById(String empid, int num, int num2);

    /**
     * 查询我的审核历史
     *
     * @param empid
     * @param num
     * @param num2
     * @return
     */
    List<Expense> find(String empid, int num, int num2);

    /**
     * 查询上传图片名称
     * @param expId
     * @return
     */
    List<ExpenseImg> findImg(int expId);
}
