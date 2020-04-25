package com.dzxy.service;

import com.dzxy.pojo.Position;

import java.util.List;

public interface PositionService {
    /**
     * 添加岗位
     */
    public int add(Position position);

    /**
     * 查询所有岗位信息
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
     * @param posid
     * @return
     */
    public Position findById(int posid);

    /**
     * 更新岗位信息
     *
     * @param position
     * @return
     */
    public int update(Position position);
}
