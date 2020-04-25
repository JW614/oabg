package com.dzxy.service.impl;

import com.dzxy.dao.PaymentDao;
import com.dzxy.pojo.Payment;
import com.dzxy.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentDao pmDao;
    public List<Payment> findPay(String startTime, String endTime,
                                 String userName, String type, int num, int num2) {

        return this.pmDao.findPay(startTime, endTime, userName, type,num,num2);
    }
}
