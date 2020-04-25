package com.dzxy.pojo;

import com.dzxy.util.PageUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class InComePage {
    private List<InCome> incomeList;
    private PageUtil pageUtil;

    public InComePage(List<InCome> incomeList, PageUtil pageUtil) {
        this.incomeList = incomeList;
        this.pageUtil = pageUtil;
    }
}
