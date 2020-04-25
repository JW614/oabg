package com.dzxy.pojo;

import com.dzxy.util.PageUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class DutyPage {
    private List<Duty> dutyList;
    private PageUtil pageUtil;

    public DutyPage(List<Duty> dutyList, PageUtil pageUtil) {
        this.dutyList = dutyList;
        this.pageUtil = pageUtil;
    }
}
