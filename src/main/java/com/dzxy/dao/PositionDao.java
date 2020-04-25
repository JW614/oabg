package com.dzxy.dao;

import com.dzxy.pojo.Position;

import java.util.List;

public interface PositionDao {
    /**
     * 添加岗位
     */
    public int save(Position position);

    /**
     * 查询所有岗位
     *
     * @return
     */
    public List<Position> findAll();

    /**
     * 删除指定编号的岗位
     *
     * @param
     * @return
     */
    public int delete(int posid);

    /**
     * 查询指定编号的岗位
     *
     * @param
     * @return
     */
    public Position findById(int posid);

    /**
     * 更新指定岗位的信息
     *
     * @param
     * @return
     */
    public int update(Position position);

}

