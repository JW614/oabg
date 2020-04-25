package com.dzxy.dao;

import com.dzxy.pojo.ExpenseImg;
import com.dzxy.pojo.ExpenseItem;

import java.util.List;

public interface ExpenseItemDao {
    /**
     * 添加一条明细
     *
     * @param item
     */
    void save(ExpenseItem item);

    /**
     * 查询指定报销单的明细
     *
     * @param expId
     * @return
     */
    List<ExpenseItem> see(int expId);

    /**
     * 保存图片名称
     * @param expenseImg
     */
    void savePhoto(ExpenseImg expenseImg);

    List<ExpenseImg> findImg(int expId);
}
