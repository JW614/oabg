package com.dzxy.service;

import com.dzxy.pojo.InCome;
import com.dzxy.pojo.Payment;

import java.util.List;

public interface InOutService {
    /**
     * 获取柱状图收入数据
     *
     * @return
     */
    public String getBarData();

    /**
     * 获取饼图支出数据
     *
     * @param time
     * @return
     */
    public String getPieData(String time);

    /**
     * 添加收入
     *
     * @param inCome
     * @return
     */
    public int incomeAdd(InCome inCome);

    /**
     * 查找符合条件的收入信息
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
