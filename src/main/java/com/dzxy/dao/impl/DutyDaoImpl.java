package com.dzxy.dao.impl;

import com.dzxy.dao.DutyDao;
import com.dzxy.pojo.Dept;
import com.dzxy.pojo.Duty;
import com.dzxy.pojo.Employee;
import com.dzxy.util.DBUtil;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class DutyDaoImpl implements DutyDao {

    public boolean find(String empId, Date now) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from duty where emprid=? and dtdate=?";
        boolean flag = false;

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            ps.setString(1, empId);
            ps.setDate(2, new java.sql.Date(now.getTime()));
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            if (rs.next()) {
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return flag;
    }

    public int save(Duty duty) {
        String sql = "insert into duty values(seq_duty.nextval,?,?,?,?,?,?)";
        Object[] objs = {duty.getEmpId(), duty.getDtdate(), duty.getSignintime(), null, duty.getIncity(), null};
        return DBUtil.executeDML(sql, objs);
    }

    public int update(Duty duty) {
        String sql = "update duty set signouttime = ?,outcity=? where emprid=? and dtdate = ?";
        Object[] objs = {duty.getSignouttime(), duty.getOutcity(), duty.getEmpId(), duty.getDtdate()};
        return DBUtil.executeDML(sql, objs);
    }

    public List<Duty> find(int deptno2, String empId2, String dtDate2,int num,int num2) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Duty> list = new ArrayList<Duty>();
        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();
        StringBuilder sql = new StringBuilder("select * from("
                +" select * from("
                +" select pro1.*,rownum r from("
                +" select dt.*,e.realname,e.deptno dn,d.* from duty dt"
                +" join employee e"
                +" on dt.emprid = e.empid"
                +" join dept d"
                +" on e.deptno = d.deptno where 1=1");
        if (deptno2 != 0) {
            sql.append(" and d.deptno = " + deptno2);
        }
        if (empId2 != null && !"".equals(empId2)) {
            sql.append(" and emprid like '%" + empId2 + "%'");
        }
        if (dtDate2 != null && !"".equals(dtDate2)) {
            sql.append(" and to_char(dtdate,'YYYY-MM-DD')>'" + dtDate2 + "'");
        }
        sql.append(" )pro1 order by rownum desc");
        sql.append(" )pro2 where pro2.r <= "+num+"");
        sql.append(" )pro3 where pro3.r >= "+num2+"");

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql.toString());
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                int dtid = rs.getInt("dtid");
                java.sql.Date dtDate = rs.getDate("dtDate");
                String signintime = rs.getString("signintime");
                String signouttime = rs.getString("signouttime");
                String incity = rs.getString("incity");
                String outcity = rs.getString("outcity");

                int deptno = rs.getInt("deptno");
                String deptname = rs.getString("deptname");
                Dept dept = new Dept(deptno, deptname);

                String empId = rs.getString("emprId");
                String realName = rs.getString("realName");
                Employee emp = new Employee();
                emp.setEmpid(empId);
                emp.setRealName(realName);
                emp.setDept(dept);
                //创建对象
                Duty duty = new Duty(dtid, dtDate, signintime, signouttime, emp);
                duty.setIncity(incity);
                duty.setOutcity(outcity);
                list.add(duty);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    public List<Duty> findById(String empid, int num, int num2) {
        String sql = "select * from("
                +" select * from("
                +" select pro1.*,rownum r from("
                +" select * from  Duty where emprid=?"
                +" )pro1 order by rownum desc"
                +" )pro2 where pro2.r <= ?"
                +" )pro3 where pro3.r >= ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Duty> list = new ArrayList<Duty>();

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
                java.sql.Date dtDate = rs.getDate("dtDate");
                String signintime = rs.getString("signintime");
                String signouttime = rs.getString("signouttime");
                String incity = rs.getString("incity");
                String outcity = rs.getString("outcity");
                //创建对象
                Duty duty = new Duty();
                duty.setDtdate(dtDate);
                duty.setSignintime(signintime);
                duty.setSignouttime(signouttime);
                duty.setIncity(incity);
                duty.setOutcity(outcity);
                list.add(duty);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    @Override
    public List<Duty> find2(int deptno2, String empId2, String dtDate2) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Duty> list = new ArrayList<Duty>();
        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();
        StringBuilder sql = new StringBuilder("select dt.*,e.realname,e.deptno,d.* from duty dt"
                +" join employee e"
                +" on dt.emprid = e.empid"
                +" join dept d"
                +" on e.deptno = d.deptno where 1=1");
        if (deptno2 != 0) {
            sql.append(" and d.deptno = " + deptno2);
        }
        if (empId2 != null && !"".equals(empId2)) {
            sql.append(" and emprid like '%" + empId2 + "%'");
        }
        if (dtDate2 != null && !"".equals(dtDate2)) {
            sql.append(" and to_char(dtdate,'YYYY-MM-DD')>'" + dtDate2 + "'");
        }

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql.toString());
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                int dtid = rs.getInt("dtid");
                java.sql.Date dtDate = rs.getDate("dtDate");
                String signintime = rs.getString("signintime");
                String signouttime = rs.getString("signouttime");
                String incity = rs.getString("incity");
                String outcity = rs.getString("outcity");

                int deptno = rs.getInt("deptno");
                String deptname = rs.getString("deptname");
                Dept dept = new Dept(deptno, deptname);

                String empId = rs.getString("emprId");
                String realName = rs.getString("realName");
                Employee emp = new Employee();
                emp.setEmpid(empId);
                emp.setRealName(realName);
                emp.setDept(dept);
                //创建对象
                Duty duty = new Duty(dtid, dtDate, signintime, signouttime, emp);
                duty.setIncity(incity);
                duty.setOutcity(outcity);
                list.add(duty);
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
