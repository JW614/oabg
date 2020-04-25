package com.dzxy.dao.impl;

import com.dzxy.dao.PaymentDao;
import com.dzxy.pojo.Employee;
import com.dzxy.pojo.ExpenseItem;
import com.dzxy.pojo.Payment;
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
public class PaymentDaoImpl implements PaymentDao {

    public int save(Payment pm) {
        String sql = "insert into payment values(seq_payment.nextval,?,?,sysdate,?,?)";
        Object[] objs = {pm.getExpEmpId(), pm.getAmount(), pm.getExpId(), pm.getAuditEmpId()};
        return DBUtil2.executeDML(sql, objs);
    }

    public List<Payment> findPay(String startTime, String endTime,
                                 String userName, String type, int num, int num2) {
        StringBuilder sql = new StringBuilder("select * from("
                + " select * from("
                + " select pro1.*,rownum r from("
                + " select item.type,item.amount,item.itemdesc,emp.realname,pm.paytime,exp.expid,exp.expdesc"
                + " from expenseitem item"
                + " join expense exp"
                + " on item.expid = exp.expid"
                + " join payment pm"
                + " on exp.expid = pm.expid"
                + " join employee emp"
                + " on pm.empid = emp.empid"
                + " where 1=1");
        if (startTime != null && !"".equals(startTime)) {
            sql.append(" and pm.paytime >= to_date('" + startTime + "','YYYY-MM-DD')");
        }
        if (endTime != null && !"".equals(endTime)) {
            sql.append(" and pm.paytime <= to_date('" + endTime + "','YYYY-MM-DD')");
        }
        if (userName != null && !"".equals(userName)) {
            sql.append(" and emp.realname='" + userName + "'");
        }
        if (type != null && !"".equals(type)) {
            sql.append(" and item.type ='" + type + "'");
        }
        sql.append(" )pro1 order by rownum desc");
        sql.append(" )pro2 where pro2.r <= "+num+"");
        sql.append(" )pro3 where pro3.r >= "+num2+"");
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Payment> list = new ArrayList<Payment>();

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql.toString());
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                String type2 = rs.getString("type");
                double amount = rs.getDouble("amount");
                String itemDesc = rs.getString("itemdesc");
                String realName = rs.getString("realname");
                Date paytime = rs.getDate("paytime");
                int expId = rs.getInt("expId");
                ExpenseItem expItem = new ExpenseItem();
                expItem.setType(type2);
                expItem.setItemDesc(itemDesc);
                Employee emp = new Employee();
                emp.setRealName(realName);
                //创建对象
                Payment pm = new Payment(amount, paytime, expId, emp, expItem);
                list.add(pm);
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
