package com.dzxy.util;

public class Constants {
    /**
     * 常量类
     */
    private Constants() {

    }

    //定义报销单状态常量   0--未审核  1--审核中  2--审核结果通过  3--审核拒绝 4--审核打回 5--已打款
    public static final String EXP_STATUS_NEW = "0";
    public static final String EXP_STATUS_AUDITING = "1";
    public static final String EXP_STATUS_PASS = "2";
    public static final String EXP_STATUS_REJECT = "3";
    public static final String EXP_STATUS_BACK = "4";
    public static final String EXP_STATUS_CASHED = "5";

    //定义具体用户角色编号
    public static final String EXP_CEOID = "jiangwen";
    public static final String EXP_CFOID = "jiangqian";

    //定义具体的报销单类型
    public static final String EXP_TYPE1 = "通信费用";
    public static final String EXP_TYPE2 = "办公室耗材";
    public static final String EXP_TYPE3 = "住宿费用";
    public static final String EXP_TYPE4 = "房租水电";
    public static final String EXP_TYPE5 = "其他";
}
