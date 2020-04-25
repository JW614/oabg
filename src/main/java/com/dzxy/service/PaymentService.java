package com.dzxy.service;

import com.dzxy.pojo.Payment;

import java.util.List;

public interface PaymentService {
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
