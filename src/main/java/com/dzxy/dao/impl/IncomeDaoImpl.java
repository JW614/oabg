package com.dzxy.dao.impl;

import com.dzxy.dao.IncomeDao;
import com.dzxy.pojo.Employee;
import com.dzxy.pojo.InCome;
import com.dzxy.util.Constants;
import com.dzxy.util.DBUtil;
import com.dzxy.util.DBUtil2;
import com.dzxy.util.DateUtil;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Component
public class IncomeDaoImpl implements IncomeDao {
    public List<Object[]> findStatisData2(String time) {
        StringBuilder sql = new StringBuilder("select item.type,sum(item.amount)"
                + " from expenseitem item"
                + " join expense exp"
                + " on item.expid = exp.expid"
                + " join payment pm"
                + " on exp.expid = pm.expid"
                + " join employee emp"
                + " on pm.empid = emp.empid"
                + " where 1=1");
        if ("1".equals(time)) {
            sql.append(" and to_char(pm.paytime,'yyyy/MM/DD')>='" + DateUtil.getNowMonthBeginTime() + "'");
        } else if ("2".equals(time)) {
            sql.append(" and to_char(pm.paytime,'yyyy/MM/DD')>='" + DateUtil.getLastYearBeginTime() + "'");
            sql.append(" and to_char(pm.paytime,'yyyy/MM/DD')<'" + DateUtil.getLastYearEndTime() + "'");
        } else if ("3".equals(time)) {
            sql.append(" and to_char(pm.paytime,'yyyy/MM/DD')>='" + DateUtil.getNowYearBeginTime() + "'");
        } else if ("4".equals(time)) {
            sql.append(" and to_char(pm.paytime,'yyyy/MM/DD')>='" + DateUtil.getNowYearBeginTime() + "'");
            sql.append(" and to_char(pm.paytime,'yyyy/MM/DD')<'" + DateUtil.getNowMonthBeginTime() + "'");
        }
        sql.append(" group by type");
        sql.append(" order by type");
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object[]> list = new ArrayList<Object[]>();

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql.toString());
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                String itemtype = rs.getString(1);
                double sumAmount = rs.getDouble(2);
                Object[] arr = null;
                if (itemtype.equals("1")) {
                    arr = new Object[]{Constants.EXP_TYPE1, sumAmount};
                } else if (itemtype.equals("2")) {
                    arr = new Object[]{Constants.EXP_TYPE2, sumAmount};
                } else if (itemtype.equals("3")) {
                    arr = new Object[]{Constants.EXP_TYPE3, sumAmount};
                } else if (itemtype.equals("4")) {
                    arr = new Object[]{Constants.EXP_TYPE4, sumAmount};
                } else if (itemtype.equals("5")) {
                    arr = new Object[]{Constants.EXP_TYPE5, sumAmount};
                }
                list.add(arr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    public List<Object[]> findStatisData() {
        String sql = "select ictype,sum(amount) from income group by ictype";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object[]> list = new ArrayList<Object[]>();

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                String ictype = rs.getString(1);
                double sumAmount = rs.getDouble(2);

                Object[] arr = {ictype, sumAmount};
                list.add(arr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    public List<Object[]> findStatisData2() {
        String sql = "select item.type,sum(item.amount)"
                + " from expenseitem item"
                + " join expense exp"
                + " on item.expid = exp.expid"
                + " join payment pm"
                + " on exp.expid = pm.expid"
                + " join employee emp"
                + " on pm.empid = emp.empid"
                + " where 1=1"
                + " group by type"
                + " order by type";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object[]> list = new ArrayList<Object[]>();

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                String itemtype = rs.getString(1);
                double sumAmount = rs.getDouble(2);

                Object[] arr = {itemtype, sumAmount};
                list.add(arr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    public int incomeAdd(InCome inCome) {
        String sql = "insert into income values(seq_income.nextval,?,?,?,?,?)";
        Object[] objs = {inCome.getAmount(), new java.sql.Date(inCome.getIdDate().getTime()), inCome.getIcType(), inCome.getInDesc(), inCome.getUserId()};
        return DBUtil2.executeDML(sql, objs);
    }

    public List<InCome> find(String startTime2, String endTime2, String icType2, int num, int num2) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InCome> list = new ArrayList<InCome>();
        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();
        StringBuilder sql = new StringBuilder("select * from("
                +" select * from("
                +" select pro1.*,rownum r from("
                +" select ic.*,emp.realname"
                +" from income ic"
                +" join employee emp"
                +" on ic.userid=emp.empid"
                +" where 1=1");
        if (startTime2 != null && !"".equals(startTime2)) {
            sql.append(" and to_char(ic.icdate,'YYYY-MM-DD')>='" + startTime2 + "'");
        }
        if (endTime2 != null && !"".equals(endTime2)) {
            sql.append(" and to_char(ic.icdate,'YYYY-MM-DD')<='" + endTime2 + "'");
        }
        if (icType2 != null && !"".equals(icType2)) {
            sql.append(" and ictype='" + icType2 + "'");
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
                int icId = rs.getInt("icid");
                double amount = rs.getDouble("amount");
                java.sql.Date icDate = rs.getDate("icDate");
                String icType = rs.getString("ictype");
                String inDesc = rs.getString("inDesc");
                String realName = rs.getString("realName");
                Employee emp = new Employee();
                emp.setRealName(realName);
                //创建对象
                InCome inCome = new InCome(icId, amount, icDate, icType, inDesc, emp);
                list.add(inCome);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    public int delete(int icId) {
        String sql = "delete from income where icid = ?";
        Object[] objs = {icId};
        return DBUtil.executeDML(sql, objs);
    }


}
