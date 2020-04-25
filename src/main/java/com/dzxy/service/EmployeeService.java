package com.dzxy.service;

import com.dzxy.pojo.Employee;

import java.util.List;

public interface EmployeeService {
    /**
     * 添加员工
     *
     * @param emp
     * @return
     */
    public int add(Employee emp);

    /**
     * 获取指定类型的员工信息
     *
     * @param type 员工类型
     * @return
     */
    public List<Employee> findByType(int type);

    /**
     * 查询所有员工
     *
     * @return
     * @param num
     * @param num2
     */
    public List<Employee> findAll(int num, int num2);

    /**
     * 多条件查询员工
     *
     * @param empId
     * @param deptno
     * @param onDuty
     * @param hireDate
     * @return
     */
    public List<Employee> findAll(int num,int num2,String empId, int deptno, int onDuty,
                                  String hireDate);

    /**
     * 删除指定ID的员工
     *
     * @param empId
     */
    public void delete(String empId);

    /**
     * 查询指定编号的员工
     */
    public Employee findById(String empId);

    /**
     * 更新员工信息
     *
     * @param emp
     * @return
     */
    public int update(Employee emp);

    /**
     * 重置密码
     *
     * @param empId
     * @return
     */
    public int resetpwd(String empId);

    /**
     * 用户登录
     *
     * @param empId
     * @param password
     * @return
     */
    public Employee login(String empId, String password);

    /**
     * 修改个人信息
     *
     * @param emp2
     * @return
     */
    public int modify(Employee emp2);

    /**
     * 查询旧密码是否正确
     *
     * @param empid
     * @param oldpwd
     * @return
     */
    public boolean exist(String empid, String oldpwd);

    /**
     * 修改用户密码
     *
     * @param empid
     * @param newpwd
     * @return
     */
    public int pwd(String empid, String newpwd);

    /**
     * 判断用户名是否可用
     * @param empId
     * @return
     */
    boolean findEmpId(String empId);
}
