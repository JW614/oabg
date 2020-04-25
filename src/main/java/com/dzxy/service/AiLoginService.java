package com.dzxy.service;

import com.dzxy.pojo.Employee;

public interface AiLoginService {
    /**
     * 人脸识别登陆
     *
     * @param empId
     * @return
     */
    Employee login(String empId);
}
