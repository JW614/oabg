package com.dzxy.service;

import com.dzxy.pojo.Dept;

import java.util.List;

public interface DeptService {
    /**
     * 添加部门
     */
    public int add(Dept dept);

    /**
     * 查询所有员工信息
     *
     * @return
     */
    public List<Dept> findAll();

    /**
     * 删除指定编号的部门
     *
     * @param
     * @return
     */
    public int delete(int deptno);

    /**
     * 查询指定编号的员工
     *
     * @param deptno
     * @return
     */
    public Dept findById(int deptno);

    /**
     * 更新部门信息
     *
     * @param dept
     * @return
     */
    public int update(Dept dept);
}
