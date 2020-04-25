package com.dzxy.service.impl;

import com.dzxy.dao.EmployeeDao;
import com.dzxy.pojo.Employee;
import com.dzxy.service.AiLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiLoginServiceImpl implements AiLoginService {
    @Autowired
    EmployeeDao employeeDao;

    @Override
    public Employee login(String empId) {
        return employeeDao.findById(empId);
    }
}
