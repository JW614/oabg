package com.dzxy.controller;

import com.dzxy.pojo.Employee;
import com.dzxy.pojo.InCome;
import com.dzxy.pojo.InComePage;
import com.dzxy.pojo.Payment;
import com.dzxy.service.InOutService;
import com.dzxy.service.PaymentService;
import com.dzxy.util.Constants;
import com.dzxy.util.DBUtil2;
import com.dzxy.util.PageUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/InOutController")
public class InOutController {
    @Autowired
    InOutService ioService;
    @Autowired
    PaymentService pmService;

    /**
     * 条件查询支出信息
     * @param currnav
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/findPay")
    public String findPay(Integer currnav,HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        if(currnav == null){
            currnav=1;
        }
        //获取当前页号
        int pagesize = 10;
        int navnum = 10;//页面导航栏维护的导航数
        //调用业务层返回数据

        //从数据库查询出来数据总行数
        int rowcount = 10;
        //获取表单数据
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String userName = request.getParameter("userName");
        String type = request.getParameter("type");
        request.setAttribute("startTime", startTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("userName", userName);
        request.setAttribute("type", type);
        request.setAttribute("EXP_TYPE1", Constants.EXP_TYPE1);
        request.setAttribute("EXP_TYPE2", Constants.EXP_TYPE2);
        request.setAttribute("EXP_TYPE3", Constants.EXP_TYPE3);
        request.setAttribute("EXP_TYPE4", Constants.EXP_TYPE4);
        request.setAttribute("EXP_TYPE5", Constants.EXP_TYPE5);
        StringBuilder sql = new StringBuilder("select count(1)"
                + " from expenseitem item"
                + " join expense exp"
                + " on item.expid = exp.expid"
                + " join payment pm"
                + " on exp.expid = pm.expid"
                + " join employee emp"
                + " on pm.empid = emp.empid"
                + " where 1=1");
        if (startTime != null && !"".equals(startTime)) {
            sql.append(" and pm.paytime >= to_date('" + startTime + "','YYYY-MM-DD')");
        }
        if (endTime != null && !"".equals(endTime)) {
            sql.append(" and pm.paytime <= to_date('" + endTime + "','YYYY-MM-DD')");
        }
        if (userName != null && !"".equals(userName)) {
            sql.append(" and emp.realname='" + userName + "'");
        }
        if (type != null && !"".equals(type)) {
            sql.append(" and item.type ='" + type + "'");
        }
        rowcount = DBUtil2.getCount(sql.toString());
        //调用pageUtil工具，获取分页导航栏数据
        PageUtil pageUtil = new PageUtil(rowcount,pagesize,currnav,navnum);
        //查询数据
        int startrow = pageUtil.getStartrow();
        int num = startrow + pagesize;
        int num2 = startrow +1;
        //调用业务层显示数据
        List<Payment> pmList = pmService.findPay(startTime, endTime, userName, type,num,num2);
        //页面跳转
        request.setAttribute("pmList", pmList);
        request.setAttribute("pageUtil",pageUtil);
        return "/inout/expenseList.html";
    }

    /**
     * 删除收入信息
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        //获取视图层删除数据编号
        int icId = Integer.parseInt(request.getParameter("icId"));
        //调用业务层删除数据
        int n = ioService.delete(icId);
        //页面跳转
        if (n > 0) {
            request.setAttribute("error", "删除成功");
            return "/inout/incomeList.html";
        } else {
            request.setAttribute("error", "删除失败");
            return "/inout/incomeList.html";
        }
    }

    /**
     * 条件查询收入信息
     * @param currnav
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/find")
    public void find(Integer currnav,HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException {
        if(currnav == null){
            currnav=1;
        }
        //获取当前页号
        int pagesize = 10;
        int navnum = 10;//页面导航栏维护的导航数
        //调用业务层返回数据

        //从数据库查询出来数据总行数
        int rowcount = 10;
        //接受来自于视图层的查询条件
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String icType = request.getParameter("icType");
        StringBuilder sql = new StringBuilder("select count(1)"
                +" from income ic"
                +" join employee emp"
                +" on ic.userid=emp.empid"
                +" where 1=1");
        if (startTime != null && !"".equals(startTime)) {
            sql.append(" and to_char(ic.icdate,'YYYY-MM-DD')>='" + startTime + "'");
        }
        if (endTime != null && !"".equals(endTime)) {
            sql.append(" and to_char(ic.icdate,'YYYY-MM-DD')<='" + endTime + "'");
        }
        if (icType != null && !"".equals(icType)) {
            sql.append(" and ictype='" + icType + "'");
        }
        rowcount = DBUtil2.getCount(sql.toString());
        //调用pageUtil工具，获取分页导航栏数据
        PageUtil pageUtil = new PageUtil(rowcount,pagesize,currnav,navnum);
        //查询数据
        int startrow = pageUtil.getStartrow();
        int num = startrow + pagesize;
        int num2 = startrow +1;
        //调用业务层获取收入信息
        List<InCome> icList = ioService.find(startTime, endTime, icType,num,num2);
        InComePage inComePage = new InComePage(icList, pageUtil);
        //将收入信息转换成jsonstr直接返回
        response.setContentType("text/html;charset=utf-8");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String jsonStr = gson.toJson(inComePage);
        response.getWriter().print(jsonStr);
    }

    /**
     * 添加收入
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/incomeAdd")
    public String incomeAdd(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        //获取表单数据
        double amount = Double.parseDouble(request.getParameter("amount"));
        String icType = request.getParameter("icType");
        Date icDate = null;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sicDate = request.getParameter("idDate");
        try {
            icDate = sdf.parse(sicDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String icDesc = request.getParameter("inDesc");
        Employee emp = (Employee) request.getSession().getAttribute("user");
        String userId = emp.getEmpid();
        //调用业务层完成数据的添加
        InCome inCome = new InCome(amount, icDate, icType, icDesc, userId);
        int n = ioService.incomeAdd(inCome);
        //页面跳转
        if (n > 0) {
            return "/inout/incomeList.html";
        } else {
            request.setAttribute("error", "添加失败");
            return "/inout/incomeAdd.html";
        }
    }

    /**
     * 支出饼状图
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/getPieData")
    public void getPieData(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        //得到时间端的值
        String time = request.getParameter("time2");

        //调用业务层获取数据
        //String jsonStr = ioService.getPieData();
        String jsonStr = ioService.getPieData(time);
        //返回数据
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(jsonStr);
    }

    /**
     * 收入柱状图
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/getBarData")
    public void getBarData(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        //String jsonStr = "[['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],[120, 200, 150, 80, 70, 110, 130]]";
        String jsonStr = ioService.getBarData();
        //返回数据
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(jsonStr);
    }
}
