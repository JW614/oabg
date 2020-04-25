package com.dzxy.dao;

import com.dzxy.pojo.Duty;

import java.util.Date;
import java.util.List;

public interface DutyDao {
    /**
     * 查询符合条件的签到考勤信息是否存在
     *
     * @param empId
     * @param now
     * @return
     */
    boolean find(String empId, Date now);

    /**
     * 添加考勤信息
     *
     * @param duty
     * @return
     */
    int save(Duty duty);

    /**
     * 更新签退时间
     *
     * @param duty
     * @return
     */
    int update(Duty duty);

    /**
     * 查询符合条件的考勤信息
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
     * 查找正登陆系统员工的考勤信息
     *
     * @param empid
     * @param num
     * @param num2
     * @return
     */
    List<Duty> findById(String empid, int num, int num2);

    List<Duty> find2(int deptno, String empId, String dtDate);
}
