package com.dzxy.service;

import com.dzxy.pojo.Duty;

import java.util.List;

public interface DutyService {
    /**
     * 用户签到
     *
     * @param empId
     * @return
     */
    int signin(String empId, String incity);

    /**
     * 用户签退
     *
     * @param empId
     * @return
     */
    int signout(String empId, String outcity);

    /**
     * 查询正登陆系统员工的考勤信息
     *
     * @param deptno
     * @param empId
     * @param dtDate
     * @param num
     * @param num2
     * @return
     */
    List<Duty> find(int deptno, String empId, String dtDate, int num, int num2);

    /**
     * 查找指定编号的员工考勤信息
     *
     * @param empid
     * @param num
     * @param num2
     * @return
     */
    List<Duty> findById(String empid, int num, int num2);

    List<Duty> find2(int deptno, String empId, String dtDate);
}
