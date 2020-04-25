package com.dzxy.dao;

import com.dzxy.pojo.Payment;

import java.util.List;

public interface PaymentDao {
    /**
     * 添加支出
     *
     * @param pm
     * @return
     */
    public int save(Payment pm);

    /**
     * 支出信息的多条件查询
     *
     * @param startTime
     * @param endTime
     * @param userName
     * @param type
     * @param num
     * @param num2
     * @return
     */
    public List<Payment> findPay(String startTime, String endTime,
                                 String userName, String type, int num, int num2);
}
