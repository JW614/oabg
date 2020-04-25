package com.dzxy.dao;

import com.dzxy.pojo.Dept;

import java.util.List;

public interface DeptDao {
    /**
     * 添加部门
     */
    public int save(Dept dept);

    /**
     * 查询所有部门
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
     * 查询指定编号的部门
     *
     * @param deptno
     * @return
     */
    public Dept findById(int deptno);

    /**
     * 更新指定部门的信息
     *
     * @param dept
     * @return
     */
    public int update(Dept dept);
}
