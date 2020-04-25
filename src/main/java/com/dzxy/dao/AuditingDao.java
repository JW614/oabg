package com.dzxy.dao;

import com.dzxy.pojo.Auditing;

import java.util.List;

public interface AuditingDao {
    /**
     * 添加审核信息
     *
     * @param auditing
     * @return
     */
    public int save(Auditing auditing);

    /**
     * 查询指定报销单编号的审核信息
     *
     * @param expId
     * @return
     */
    public List<Auditing> check(int expId);
}
