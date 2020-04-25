package com.dzxy.dao;

import com.dzxy.pojo.InCome;

import java.util.List;

public interface IncomeDao {
    public List<Object[]> findStatisData();

    /**
     * 查询支出的统计数据
     *
     * @param time
     * @return
     */
    public List<Object[]> findStatisData2(String time);

    /**
     * 添加收入信息
     *
     * @param inCome
     * @return
     */
    public int incomeAdd(InCome inCome);

    /**
     * 查询符合条件的收入信息
     *
     * @param startTime
     * @param endTime
     * @param icType
     * @param num
     * @param num2
     * @return
     */
    public List<InCome> find(String startTime, String endTime, String icType, int num, int num2);

    /**
     * 删除指定编号的收入信息
     *
     * @param icId
     * @return
     */
    public int delete(int icId);
}
