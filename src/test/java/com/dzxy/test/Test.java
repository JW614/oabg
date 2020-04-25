package com.dzxy.test;

import com.dzxy.util.DBUtil2;

public class Test {
    public static void main(String[] args) {
        String empid = "jiangqian";
        String sql = "select count(1) from expense where empid= '"+empid+"'";
        int rowcount = DBUtil2.getCount(sql);
        System.out.println(rowcount);
    }
}
