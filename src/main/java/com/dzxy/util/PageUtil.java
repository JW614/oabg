package com.dzxy.util;

import java.util.List;

/**
 * 分页工具类的封装
 *
 */
public class PageUtil {
    /**
     * 1 数据里面的数据总行数
     */
    private int rowcount;// 数据里面的数据总行数
    /**
     * 2  一页多少行数据
     */
    private int pagesize=10;// 一页多少行数据
    /**
     * 3 查询起始行    limit startrow,pagesize;
     */
    private int startrow;//查询起始行    limit startrow,pagesize;
    /**
     * 4 当前点击的导航   当前页
     */
    private int currnav;//当前点击的导航
    /**
     * 5 页面维护的固定导航个数
     */
    private int navnum;//页面维护的固定导航个数
    /**
     * 6 页面数据整体导航个数  总页数
     */
    private int navcount;//页面数据整体导航个数
    /**
     * 7 页面展示的起始导航
     */
    private int begin;//页面展示的起始导航
    /**
     * 8 页面维护展示的结束导航
     */
    private int end;//页面维护展示的结束导航
    /**
     * 9 上一页
     */
    private int prev;// 上一页
    /**
     * 10 下一页
     */
    private int next;//下一页
    /**
     * 11 首页
     */
    private int first=1;//首页
    /**
     * 12 尾页
     */
    private int last;//尾页
    private List pageData;//存放分页的数据
    /**
     * 必要的数据项 ：
     * 		计算出 其他的数据项：
     * 		   数据总行数   rowcount  ； 一页显示的数据行数  pagesize
     * 		 导航栏  导航的个数  navnum  ；当前页导航   currnav
     *
     */
    public PageUtil(int rowcount, int pagesize, int currnav, int navnum) {
        super();
        this.rowcount = rowcount;
        this.pagesize = pagesize;
        this.currnav = currnav;
        this.navnum = navnum;

        /**
         * 根据已有的数据项，计算其他的数据项
         */
        /**
         * 3 查询起始行    limit startrow,pagesize;
         */

        this.startrow = (this.currnav - 1) * pagesize;
        /**
         * 6 总页数  navcount？？？？？？？？  round问题
         * 		ceil函数 向上取整
         */
        this.navcount = (int)Math.ceil(rowcount*1.0/pagesize);
        /**
         * 7 起始导航
         * 			6    6-5 this.begin =1
         * 			当前页 5 5-5 =0；
         * 			     4 -5 =-1
         */
        this.begin = Math.max(this.currnav -(this.navnum/2),1);
        /**
         * 8  结尾导航
         */
        this.end = Math.min(this.begin + this.navnum-1 , this.navcount);
        /**
         *  9  上一页
         *  		如果是第一页  没有上一页
         *  		1 页
         *  		0 1 比较   取最大值
         */
        this.prev = Math.max(this.currnav - 1, 1);
        /**
         * 10 下一页
         * 			如果是最后一页  没有下一页
         * 			76 + 1 = 77  77没有
         * 			76 和77 比较  取最小
         */
        this.next = Math.min(this.currnav + 1, this.navcount);

        /**
         * 12 尾页
         */
        this.last = this.navcount;
        /**
         * 特殊情况处理
         * 		一共  760 行数据
         * 				当前点击的导航页  65
         * 			int rowcount = 760 ; //数据总行数
         int pagesize = 10 ; //每页显示的行数
         int currnav = 75; //当前点击的导航 页 当前页
         结果：错误
         this.begin = 70;
         this.end = 76
         应该是：	end -begin = 9
         */
        if(this.end - this.begin < 9 ){
            this.begin = Math.max(this.end - 9 ,1);
        }


    }
    public PageUtil() {
        super();
    }
    public int getRowcount() {
        return rowcount;
    }
    public void setRowcount(int rowcount) {
        this.rowcount = rowcount;
    }
    public int getPagesize() {
        return pagesize;
    }
    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
    public int getStartrow() {
        return startrow;
    }
    public void setStartrow(int startrow) {
        this.startrow = startrow;
    }
    public int getCurrnav() {
        return currnav;
    }
    public void setCurrnav(int currnav) {
        this.currnav = currnav;
    }
    public int getNavnum() {
        return navnum;
    }
    public void setNavnum(int navnum) {
        this.navnum = navnum;
    }
    public int getNavcount() {
        return navcount;
    }
    public void setNavcount(int navcount) {
        this.navcount = navcount;
    }
    public int getBegin() {
        return begin;
    }
    public void setBegin(int begin) {
        this.begin = begin;
    }
    public int getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
    }
    public int getPrev() {
        return prev;
    }
    public void setPrev(int prev) {
        this.prev = prev;
    }
    public int getNext() {
        return next;
    }
    public void setNext(int next) {
        this.next = next;
    }
    public int getFirst() {
        return first;
    }
    public void setFirst(int first) {
        this.first = first;
    }
    public int getLast() {
        return last;
    }
    public void setLast(int last) {
        this.last = last;
    }
    public List getPageData() {
        return pageData;
    }
    public void setPageData(List pageData) {
        this.pageData = pageData;
    }

    public static void main(String[] args) {
        PageUtil pageUtil = new PageUtil();
        // 获取上一页  下一页  尾页
        pageUtil.getPrev();
        pageUtil.getNext();
        pageUtil.getLast();
    }

}
