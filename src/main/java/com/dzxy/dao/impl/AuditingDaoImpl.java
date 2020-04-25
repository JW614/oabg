package com.dzxy.dao.impl;

import com.dzxy.dao.AuditingDao;
import com.dzxy.pojo.Auditing;
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
public class AuditingDaoImpl implements AuditingDao {

    public int save(Auditing auditing) {
        String sql = "insert into auditing values(seq_auditing.nextval,?,?,?,?,sysdate)";
        Object[] objs = {auditing.getExpId(), auditing.getAuditorId(),
                auditing.getResult(), auditing.getAuditDesc()};
        return DBUtil.executeDML(sql, objs);
    }

    public List<Auditing> check(int expId) {
        String sql = "select exp.*,emp.realName"
                + " from AUDITING exp"
                + " join employee emp"
                + " on exp.empid = emp.empid"
                + " where expid=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Auditing> list = new ArrayList<Auditing>();

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            ps.setInt(1, expId);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                String result = rs.getString("result");
                Date auditTime = rs.getDate("Time");
                String auditDesc = rs.getString("auditDesc");
                String realName = rs.getString("realName");
                Employee auditor = new Employee();
                auditor.setRealName(realName);
                //创建对象
                Auditing expenseItem = new Auditing(result, auditDesc, auditTime, expId, auditor);

                list.add(expenseItem);
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
