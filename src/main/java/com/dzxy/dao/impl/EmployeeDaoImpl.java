package com.dzxy.dao.impl;

import com.dzxy.dao.EmployeeDao;
import com.dzxy.pojo.Dept;
import com.dzxy.pojo.Employee;
import com.dzxy.pojo.Position;
import com.dzxy.util.DBUtil;
import com.dzxy.util.MD5Util;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class EmployeeDaoImpl implements EmployeeDao {

    public int save(Employee emp) {
        String sql = "insert into employee values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] objs = {
                emp.getEmpid(),
                emp.getPassword(),
                emp.getDept().getDeptno(),
                emp.getPosition().getPosid(),
                emp.getMgr().getEmpid(),
                emp.getRealName(),
                emp.getSex(),
                new java.sql.Date(emp.getBirthDate().getTime()),
                new java.sql.Date(emp.getHireDate().getTime()),
                null,
                emp.getOnDuty(),
                emp.getEmpType(),
                emp.getPhone(),
                emp.getQq(),
                emp.getEmerContectPerson(),
                emp.getIdCard()
        };
        if (emp.getLeaveDate() != null) {
            objs = new Object[]{
                    emp.getEmpid(),
                    emp.getPassword(),
                    emp.getDept().getDeptno(),
                    emp.getPosition().getPosid(),
                    emp.getMgr().getEmpid(),
                    emp.getRealName(),
                    emp.getSex(),
                    new java.sql.Date(emp.getBirthDate().getTime()),
                    new java.sql.Date(emp.getHireDate().getTime()),
                    new java.sql.Date(emp.getLeaveDate().getTime()),
                    emp.getOnDuty(),
                    emp.getEmpType(),
                    emp.getPhone(),
                    emp.getQq(),
                    emp.getEmerContectPerson(),
                    emp.getIdCard()
            };
        }
        return DBUtil.executeDML(sql, objs);
    }

    public List<Employee> findByType(int type) {
        String sql = "select * from  employee where emptype=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Employee> list = new ArrayList<Employee>();

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            ps.setInt(1, type);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                String empid = rs.getString("empid");
                String realname = rs.getString("realname");

                //创建对象
                Employee mgr = new Employee();
                mgr.setEmpid(empid);
                mgr.setRealName(realname);
                list.add(mgr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    /**
     * 查询所有员工
     */
    public List<Employee> findAll(int num, int num2) {
        String sql = "select * from("
                +" select * from("
                +" select pro1.*,rownum r from("
                +" select e.empid,e.realname,e.sex,e.hiredate,e.phone,d.deptno,d.deptname,p.posid,"
                +" p.pname,mgr.empid a,mgr.realname b"
                +" from employee e"
                +" join dept d"
                +" on e.deptno = d.deptno"
                +" join position p"
                +" on e.posid = p.posid"
                +" left join employee mgr"
                +" on e.mgrid = mgr.empid"
                +" )pro1 order by rownum desc"
                +" )pro2 where pro2.r <= ?"
                +" )pro3 where pro3.r >= ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Employee> list = new ArrayList<Employee>();

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            ps.setInt(1, num);
            ps.setInt(2, num2);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                String empId = rs.getString("empid");
                String empRealName = rs.getString("realname");
                String sex = rs.getString("sex");
                Date hireDate = rs.getDate("hiredate");
                String phone = rs.getString("phone");

                int deptno = rs.getInt("deptno");
                String dname = rs.getString("deptname");
                Dept dept = new Dept(deptno, dname);
                int posId = rs.getInt("posid");
                String pname = rs.getString("pname");
                Position position = new Position(posId, pname);
                String mgrId = rs.getString(10);
                String mgrRealName = rs.getString(11);
                Employee mgr = new Employee(mgrId, mgrRealName);
                //创建对象
                Employee emp = new Employee(empId, empRealName, sex, hireDate, phone, dept, position, mgr);
                list.add(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    public List<Employee> find(int num,int num2,String empId2, int deptno2, int onDuty2,
                               String hireDate2) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Employee> list = new ArrayList<Employee>();
        StringBuilder sql = new StringBuilder("select * from("
                + " select * from("
                + " select pro1.*,rownum r from("
                + " select e.empid,e.realname,e.sex,e.hiredate,e.phone,d.deptno,d.deptname,p.posid,"
                + " p.pname,mgr.empid a,mgr.realname b"
                + " from employee e"
                + " join dept d"
                + " on e.deptno = d.deptno"
                + " join position p"
                + " on e.posid = p.posid"
                + " left join employee mgr"
                + " on e.mgrid = mgr.empid where 1=1");
        if (empId2 != null) {//&& !"".equals(empId2)
            sql.append(" and e.empid like '%" + empId2 + "%'");
        }
        if (deptno2 != 0) {
            sql.append(" and d.deptno =" + deptno2 + "");
        }
        if (onDuty2 >= 0) {
            sql.append(" and e.onduty = " + onDuty2 + "");
        }
        if (hireDate2 != null && !"".equals(hireDate2)) {
            sql.append(" and e.hiredate > to_date('" + hireDate2 + "','YYYY-MM-DD')");
        }
        sql.append(" )pro1 order by rownum desc");
        sql.append(" )pro2 where pro2.r <= "+num+"");
        sql.append(" )pro3 where pro3.r >= "+num2+"");
        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql.toString());
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                String empId = rs.getString("empid");
                String empRealName = rs.getString("realname");
                String sex = rs.getString("sex");
                Date hireDate = rs.getDate("hiredate");
                String phone = rs.getString("phone");

                int deptno = rs.getInt("deptno");
                String dname = rs.getString("deptname");
                Dept dept = new Dept(deptno, dname);
                int posId = rs.getInt("posid");
                String pname = rs.getString("pname");
                Position position = new Position(posId, pname);
                String mgrId = rs.getString(10);
                String mgrRealName = rs.getString(11);
                Employee mgr = new Employee(mgrId, mgrRealName);
                //创建对象
                Employee emp = new Employee(empId, empRealName, sex, hireDate, phone, dept, position, mgr);
                list.add(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    public void delete(String empId) {
        String sql = "delete from employee where empid = ?";
        Object[] objs = {empId};
        DBUtil.executeDML(sql, objs);

    }

    public Employee findById(String empId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Employee emp = null;

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement("select * from employee where empId = ?");
            ps.setString(1, empId);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            if (rs.next()) {
                //String empId = rs.getString("empid");
                String password = rs.getString("password");
                String empRealName = rs.getString("realname");
                String sex = rs.getString("sex");
                Date birthDate = rs.getDate("birthdate");
                Date hireDate = rs.getDate("hiredate");
                Date leaveDate = rs.getDate("leavedate");
                int onDuty = rs.getInt("onDuty");
                int empType = rs.getInt("empType");
                String phone = rs.getString("phone");
                String qq = rs.getString("qq");
                String emerContectPerson = rs.getString("emercontactperson");
                String idCard = rs.getString("idCard");
                int deptno = rs.getInt("deptno");
                Dept dept = new Dept();
                dept.setDeptno(deptno);
                int posId = rs.getInt("posid");
                Position position = new Position();
                position.setPosid(posId);
                String mgrId = rs.getString("mgrId");
                Employee mgr = new Employee();
                mgr.setEmpid(mgrId);
                //创建对象
                emp = new Employee(empId, password, empRealName, sex, birthDate, hireDate, leaveDate, onDuty, empType, phone, qq, emerContectPerson, idCard, dept, position, mgr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return emp;
    }

    public int update(Employee emp) {
        String sql = "update EMPLOYEE set password=?,deptno=?,posid=?,mgrid=?,"
                + " realname=?,sex=?,birthdate=?,hiredate=?,leavedate=?,"
                + " onduty=?,emptype=?,phone=?,qq=?,emercontactperson=?,idcard=? where empid=?";
        Object[] objs = {
                emp.getPassword(),
                emp.getDept().getDeptno(),
                emp.getPosition().getPosid(),
                emp.getMgr().getEmpid(),
                emp.getRealName(),
                emp.getSex(),
                new java.sql.Date(emp.getBirthDate().getTime()),
                new java.sql.Date(emp.getHireDate().getTime()),
                null,
                emp.getOnDuty(),
                emp.getEmpType(),
                emp.getPhone(),
                emp.getQq(),
                emp.getEmerContectPerson(),
                emp.getIdCard(),
                emp.getEmpid()
        };
        if (emp.getLeaveDate() != null) {
            objs = new Object[]{
                    emp.getPassword(),
                    emp.getDept().getDeptno(),
                    emp.getPosition().getPosid(),
                    emp.getMgr().getEmpid(),
                    emp.getRealName(),
                    emp.getSex(),
                    new java.sql.Date(emp.getBirthDate().getTime()),
                    new java.sql.Date(emp.getHireDate().getTime()),
                    new java.sql.Date(emp.getLeaveDate().getTime()),
                    emp.getOnDuty(),
                    emp.getEmpType(),
                    emp.getPhone(),
                    emp.getQq(),
                    emp.getEmerContectPerson(),
                    emp.getIdCard(),
                    emp.getEmpid()
            };
        }
        return DBUtil.executeDML(sql, objs);
    }

    public int resetpwd(String empId) {
        String sql = "update EMPLOYEE set password='123456' where empid=?";
        Object[] objs = {empId};
        return DBUtil.executeDML(sql, objs);

    }

    public int modify(Employee emp) {
        String sql = "update EMPLOYEE set phone=?,qq=?,emercontactperson=? where empid=?";
        Object[] objs = {
                emp.getPhone(),
                emp.getQq(),
                emp.getEmerContectPerson(),
                emp.getEmpid()
        };
        return DBUtil.executeDML(sql, objs);
    }

    public boolean exist(String empid, String oldpwd) {
        String sql = "select * from EMPLOYEE where empid=? and password=?";
        Object[] objs = {empid, oldpwd};
        int n = DBUtil.executeDML(sql, objs);
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int pwd(String empid, String newpwd) {
        String sql = "update EMPLOYEE set password=? where empid=?";
        Object[] objs = null;
        if ("123456".equals(newpwd)) {
            objs = new Object[]{newpwd, empid};
        } else {
            objs = new Object[]{MD5Util.getMD5(newpwd), empid};
        }
        return DBUtil.executeDML(sql, objs);
    }

    @Override
    public boolean findEmpId(String empId) {
        String sql = "select * from EMPLOYEE where empid=?";
        Object[] objs = {empId};
        int n = DBUtil.executeDML(sql, objs);
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }
}
