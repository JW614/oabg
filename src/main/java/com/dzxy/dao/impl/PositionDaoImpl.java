package com.dzxy.dao.impl;

import com.dzxy.dao.PositionDao;
import com.dzxy.pojo.Position;
import com.dzxy.util.DBUtil;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Component
public class PositionDaoImpl implements PositionDao {

    public int save(Position position) {
        String sql = "insert into position values(?,?,?)";
        Object[] objs = {position.getPosid(), position.getPname(), position.getPdesc()};
        return DBUtil.executeDML(sql, objs);
    }

    public List<Position> findAll() {
        String sql = "select * from  position order by posid asc";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Position> list = new ArrayList<Position>();

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            while (rs.next()) {
                int posid = rs.getInt("posid");
                String pname = rs.getString("pname");
                String pdesc = rs.getString("pdesc");

                //创建对象
                Position position = new Position(posid, pname, pdesc);
                list.add(position);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return list;
    }

    public int delete(int posid) {
        String sql = "delete from position where posid = ?";
        Object[] objs = {posid};
        return DBUtil.executeDML(sql, objs);
    }

    public Position findById(int posid) {
        String sql = "select * from  position where posid=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Position position = null;

        // 1 获取连接对象
        Connection conn = DBUtil.getConnection();

        try {
            // 2 创建预处理块对象
            ps = conn.prepareStatement(sql);
            ps.setInt(1, posid);
            // 3 执行sql语句 ，返回结果
            rs = ps.executeQuery();
            // 4 遍历结果集  ， 将数据放到对象中， 放到list中
            if (rs.next()) {
                String pname = rs.getString("pname");
                String pdesc = rs.getString("pdesc");
                //创建对象
                position = new Position(posid, pname, pdesc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 释放资源
            DBUtil.closeAll(rs, ps, conn);
        }
        return position;
    }

    public int update(Position position) {
        String sql = "update position set pname=?,pdesc=? where posid=?";
        Object[] objs = {position.getPname(), position.getPdesc(), position.getPosid()};
        return DBUtil.executeDML(sql, objs);
    }

}
