package com.dzxy.dao.impl;

import com.dzxy.dao.ExpenseItemDao;
import com.dzxy.pojo.ExpenseImg;
import com.dzxy.pojo.ExpenseItem;
import com.dzxy.util.DBUtil;
import com.dzxy.util.DBUtil2;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Component
public class ExpenseItemDaoImpl implements ExpenseItemDao {

    public void save(ExpenseItem item) {
        String sql = "insert into expenseitem values(seq_expitem.nextval,?,?,?,?)";
        Object[] objs = {item.getExpId(), item.getType(), item.getAmoumt(), item.getItemDesc()};
        DBUtil2.executeDML(sql, objs);
    }

    public List<ExpenseItem> see(int expId) {
        String sql = "select * from  ExpenseItem where expId=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ExpenseItem> list = new ArrayList<ExpenseItem>();

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
                String type = rs.getString("type");
                double amoumt = rs.getDouble("amount");
                String itemDesc = rs.getString("itemDesc");
                //创建对象
                ExpenseItem expenseItem = new ExpenseItem(type, amoumt, itemDesc, expId);

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

    @Override
    public void savePhoto(ExpenseImg expenseImg) {
        String sql = "insert into EXPENSEPHOTO values(seq_expphoto.nextval,?,?)";
        Object[] objs = {expenseImg.getNextVal(),expenseImg.getPhoto()};
        DBUtil2.executeDML(sql, objs);
    }

    @Override
    public List<ExpenseImg> findImg(int expId) {
        String sql = "select * from  EXPENSEPHOTO where expId=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ExpenseImg> list = new ArrayList<>();
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
                int expId1 = rs.getInt("expid");
                String photo = rs.getString("photo");
                //创建对象
                ExpenseImg expenseImg = new ExpenseImg();
                expenseImg.setNextVal(expId1);
                expenseImg.setPhoto(photo);
                list.add(expenseImg);
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
