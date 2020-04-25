package com.dzxy.dao.impl;

import com.dzxy.dao.DeptDao;
import com.dzxy.pojo.Dept;
import com.dzxy.util.DBUtil;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Component
public class DeptDaoImpl implements DeptDao {

    public int save(Dept dept) {
        String sql = "insert into dept values(?,?,?)";
        Object[] objs = {dept.getDeptno(), dept.getDeptname(), dept.getLocation()};
        return DBUtil.executeDML(sql, objs);
    }

    public List<Dept> findAll() {
        String sql = "select * from  dept order by deptno asc";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Dept> list = new ArrayList<Dept>();

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                int deptno = rs.getInt("deptno");
                String deptname = rs.getString("deptname");
                String location = rs.getString("location");

                //创建对象
                Dept dept = new Dept(deptno, deptname, location);
                list.add(dept);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    public int delete(int deptno) {
        String sql = "delete from dept where deptno = ?";
        Object[] objs = {deptno};
        return DBUtil.executeDML(sql, objs);
    }

    public Dept findById(int deptno) {
        String sql = "select * from  dept where deptno=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Dept dept = null;

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            ps.setInt(1, deptno);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            if (rs.next()) {
                String deptname = rs.getString("deptname");
                String location = rs.getString("location");
                //创建对象
                dept = new Dept(deptno, deptname, location);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return dept;
    }

    public int update(Dept dept) {
        String sql = "update dept set deptname=?,location=? where deptno=?";
        Object[] objs = {dept.getDeptname(), dept.getLocation(), dept.getDeptno()};
        return DBUtil.executeDML(sql, objs);
    }

}
