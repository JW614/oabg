package com.dzxy.dao;

import com.dzxy.pojo.Expense;

import java.util.List;

public interface ExpenseDao {
    /**
     * 获取序列的下一个值（作为报销单的编号）
     *
     * @return
     */
    int getNextVal();

    /**
     * 添加一个报销单
     *
     * @param exp
     */
    void save(Expense exp);

    /**
     * 查询指定用户要审核的报销单
     *
     * @param empId
     * @param num
     * @param num2
     * @return
     */
    List<Expense> findByEmpId(String empId, int num, int num2);

    /**
     * 修改报销单状态
     *
     * @param exp
     */
    void update(Expense exp);

    /**
     * 查询指定编号的报销单
     *
     * @param expId
     * @return
     */
    Expense findById(int expId);

    /**
     * 查询登录人的报销单
     *
     * @param empid
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
}
