package com.dzxy.dao.impl;

import com.dzxy.dao.ExpenseDao;
import com.dzxy.pojo.Auditing;
import com.dzxy.pojo.Employee;
import com.dzxy.pojo.Expense;
import com.dzxy.util.DBUtil;
import com.dzxy.util.DBUtil2;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class ExpenseDaoImpl implements ExpenseDao {

    public int getNextVal() {
        String sql = "select seq_exp.nextval from dual";
        PreparedStatement ps = null;
        ResultSet rs = null;

        int nextVal = 0;
        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            rs.next();
            nextVal = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return nextVal;
    }

    public void save(Expense exp) {
        String sql = "insert into expense values(?,?,?,?,?,?,?,?)";
        Object[] objs = {exp.getExpId(), exp.getEmpId(), exp.getTotalAmount(),
                new java.sql.Timestamp(exp.getExpTime().getTime()), exp.getExpDesc(),
                exp.getNextAuditorId(), exp.getLastResult(), exp.getStatus()};
        DBUtil2.executeDML(sql, objs);

    }

    public List<Expense> findByEmpId(String autidorId, int num, int num2) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Expense> list = new ArrayList<Expense>();
        String sql = "select * from("
                +" select * from("
                +" select pro1.*,rownum r from("
                +" select exp.*,emp.realName "
                + " from expense exp "
                + " join employee emp"
                + " on exp.empid = emp.empid"
                + " where nextauditor=?"
                +" )pro1 order by rownum desc"
                +" )pro2 where pro2.r <= ?"
                +" )pro3 where pro3.r >= ?";
        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            ps.setString(1, autidorId);
            ps.setInt(2, num);
            ps.setInt(3, num2);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                int expId = rs.getInt("expId");
                double totalAmount = rs.getDouble("totalAmount");
                Date expTime = rs.getDate("expTime");
                String expDesc = rs.getString("expDesc");
                String lastResult = rs.getString("lastResult");
                String status = rs.getString("status");
                String empId = rs.getString("empId");
                String realName = rs.getString("realName");
                Employee emp = new Employee(empId, realName);
                //创建对象
                Expense exp = new Expense(expId, totalAmount, expTime, expDesc, lastResult, status, emp);
                list.add(exp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    public void update(Expense exp) {
        String sql = "update expense set nextauditor=?,lastresult=?,status=? where expid=?";
        Object[] objs = {exp.getNextAuditorId(), exp.getLastResult(), exp.getStatus(), exp.getExpId()};
        DBUtil2.executeDML(sql, objs);
    }

    public Expense findById(int expId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        //List<Expense> list = new ArrayList<Expense>();
        Expense exp = null;
        String sql = "select * from expense where expId=?";
        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            ps.setInt(1, expId);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            if (rs.next()) {
                double totalAmount = rs.getDouble("totalAmount");
                Date expTime = rs.getDate("expTime");
                String expDesc = rs.getString("expDesc");
                String lastResult = rs.getString("lastResult");
                String status = rs.getString("status");
                String empId = rs.getString("empId");
                //String realName = rs.getString("realName");
                Employee emp = new Employee(empId, null);
                //创建对象
                exp = new Expense(expId, totalAmount, expTime, expDesc, lastResult, status, emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return exp;
    }

    public List<Expense> findById(String empid, int num, int num2) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Expense> list = new ArrayList<Expense>();
        String sql = "select * from("
                +" select * from("
                +" select pro1.*,rownum r from("
                +" select * from Expense where empid=?"
                +" )pro1 order by rownum desc"
                +" )pro2 where pro2.r <= ?"
                +" )pro3 where pro3.r >= ?";
        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            ps.setString(1, empid);
            ps.setInt(2, num);
            ps.setInt(3, num2);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                int expId = rs.getInt("expId");
                double totalAmount = rs.getDouble("totalAmount");
                Date expTime = rs.getDate("expTime");
                String expDesc = rs.getString("expDesc");
                String status = rs.getString("status");
                //创建对象
                Expense exp = new Expense();
                exp.setExpId(expId);
                exp.setTotalAmount(totalAmount);
                exp.setExpTime(expTime);
                exp.setExpDesc(expDesc);
                exp.setStatus(status);
                list.add(exp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    public List<Expense> find(String empid, int num, int num2) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Expense> list = new ArrayList<Expense>();
        String sql = "select * from("
                +" select * from("
                +" select pro1.*,rownum r from("
                +" select emp.realname,exp.expId,exp.totalamount,exp.exptime,exp.expdesc,au.time,au.result from expense exp"
                + " join employee emp"
                + " on exp.empId = emp.empid"
                + " join AUDITING au"
                + " on au.expid = exp.expid"
                + " where au.empid=?"
                +" )pro1 order by rownum desc"
                +" )pro2 where pro2.r <= ?"
                +" )pro3 where pro3.r >= ?";
        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            ps.setString(1, empid);
            ps.setInt(2, num);
            ps.setInt(3, num2);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                int expId = rs.getInt("expid");
                double totalAmount = rs.getDouble("totalAmount");
                Date expTime = rs.getDate("expTime");
                String expDesc = rs.getString("expDesc");
                String realName = rs.getString("realName");
                Date auditTime = rs.getDate("time");
                String result = rs.getString("result");
                Employee emp = new Employee(null, realName);
                Auditing auditing = new Auditing();
                auditing.setAuditTime(auditTime);
                auditing.setResult(result);
                //创建对象
                Expense exp = new Expense(expId, totalAmount, expTime, expDesc, emp, auditing);
                list.add(exp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

}
