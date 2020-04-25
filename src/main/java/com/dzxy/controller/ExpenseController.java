package com.dzxy.controller;

import com.dzxy.pojo.*;
import com.dzxy.service.ExpenseService;
import com.dzxy.util.DBUtil2;
import com.dzxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/ExpenseController")
public class ExpenseController {
    private static List<ExpenseImg> list = new ArrayList<>();
    @Autowired
    ExpenseService expService;

    /**
     * 报销单打回后修改数据时，将数据显示在表单中，供修改
     * @param expId
     * @param request
     * @return
     */
    @RequestMapping("/findByExpId")
    public String findByExpId (int expId,HttpServletRequest request){
        //调用业务层查询指定报销单的信息
        List<ExpenseItem> exp2List = expService.see(expId);
        //页面跳转
        request.setAttribute("exp2List", exp2List);
        return "/expense/expenseUpdate";
    }

    /**
     * 查看报销单图片
     * @param expId
     * @param i
     * @param request
     * @return
     */
    @RequestMapping("/expenseImg")
    public String expenseImg(int expId,int i,HttpServletRequest request){
        List<ExpenseImg> imgList = expService.findImg(expId);
        if(i==1){
            request.setAttribute("url","/ExpenseController/looked");
        }else if(i==2){
            request.setAttribute("url","/ExpenseController/findById");
        }else if(i==3){
            request.setAttribute("url","/ExpenseController/toAudit");
        }
        request.setAttribute("imgList",imgList);
        return "/expense/expenseImg.html";
    }

    /**
     * 查看审核记录
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/check3")
    public String check3(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        //获取报销单编号
        int expId = 0;
        try {
            expId = Integer.parseInt(request.getParameter("expId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //调用业务层查询指定报销单的审核信息
        List<Auditing> auditingList = expService.check(expId);
        //页面跳转
        request.setAttribute("auditingList", auditingList);
        return "/expense/auditHistory3.html";
    }

    /**
     * 查看具体报销项
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/see3")
    public String see3(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException {
        //获取报销单编号
        int expId = 0;
        try {
            expId = Integer.parseInt(request.getParameter("expId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //调用业务层查询指定报销单的信息
        List<ExpenseItem> exp2List = expService.see(expId);
        //页面跳转
        request.setAttribute("exp2List", exp2List);
        return "/expense/expenseDetail3.html";
    }

    /**
     * 我的审核历史
     * @param currnav
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/looked")
    public String looked(Integer currnav,HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        if(currnav == null){
            currnav=1;
        }
        //获取正在登陆人的ID
        Employee emp = (Employee) request.getSession().getAttribute("user");
        String empid = emp.getEmpid();
        //获取当前页号
        int pagesize = 10;
        int navnum = 10;//页面导航栏维护的导航数
        //调用业务层返回数据

        //从数据库查询出来数据总行数
        int rowcount = 10;
        String sql = "select count(1) from expense exp join employee emp on exp.empId = emp.empid join AUDITING au on au.expid = exp.expid where au.empid='"+empid+"'";
        rowcount = DBUtil2.getCount(sql);
        //调用pageUtil工具，获取分页导航栏数据
        PageUtil pageUtil = new PageUtil(rowcount,pagesize,currnav,navnum);
        //查询数据
        int startrow = pageUtil.getStartrow();
        int num = startrow + pagesize;
        int num2 = startrow +1;
        //调用业务层查找符合条件的数据
        List<Expense> exp4List = expService.find(empid,num,num2);
        //跳转到指定页面
        request.setAttribute("exp4List", exp4List);
        request.setAttribute("pageUtil",pageUtil);
        return "/expense/myAudit.html";
    }

    /**
     * 查看审核记录
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/check2")
    public String check2(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        //获取报销单编号
        int expId = 0;
        try {
            expId = Integer.parseInt(request.getParameter("expId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //调用业务层查询指定报销单的审核信息
        List<Auditing> auditingList = expService.check(expId);
        //页面跳转
        request.setAttribute("auditingList", auditingList);
        return "/expense/auditHistory2.html";
    }

    /**
     * 查看具体报销项
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/see2")
    public String see2(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException {
        //获取报销单编号
        int expId = 0;
        try {
            expId = Integer.parseInt(request.getParameter("expId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //调用业务层查询指定报销单的信息
        List<ExpenseItem> exp2List = expService.see(expId);
        //页面跳转
        request.setAttribute("exp2List", exp2List);
        return "/expense/expenseDetail2.html";
    }

    /**
     * 我的报销单
     * @param currnav
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/findById")
    public String findById(Integer currnav,HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        if(currnav == null){
            currnav=1;
        }
        //获取正在登陆人的ID
        Employee emp = (Employee) request.getSession().getAttribute("user");
        String empid = emp.getEmpid();
        //获取当前页号
        int pagesize = 10;
        int navnum = 10;//页面导航栏维护的导航数
        //调用业务层返回数据

        //从数据库查询出来数据总行数
        int rowcount = 10;
        String sql = "select count(1) from expense where empid= '"+empid+"'";
        rowcount = DBUtil2.getCount(sql);
        //调用pageUtil工具，获取分页导航栏数据
        PageUtil pageUtil = new PageUtil(rowcount,pagesize,currnav,navnum);
        //查询数据
        int startrow = pageUtil.getStartrow();
        int num = startrow + pagesize;
        int num2 = startrow +1;
        //调用业务层查找符合条件的数据
        List<Expense> exp3List = expService.findById(empid,num,num2);
        //跳转到指定页面
        request.setAttribute("exp3List", exp3List);
        request.setAttribute("pageUtil",pageUtil);
        return "/expense/myExpense.html";
    }

    /**
     * 查看审核记录
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/check")
    public String check(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //获取报销单编号
        int expId = 0;
        try {
            expId = Integer.parseInt(request.getParameter("expId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //调用业务层查询指定报销单的审核信息
        List<Auditing> auditingList = expService.check(expId);
        //页面跳转
        request.setAttribute("auditingList", auditingList);
        return "/expense/auditHistory.html";
    }

    /**
     * 查看具体报销项
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/see")
    public String see(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {
        //获取报销单编号
        int expId = 0;
        try {
            expId = Integer.parseInt(request.getParameter("expId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //调用业务层查询指定报销单的信息
        List<ExpenseItem> exp2List = expService.see(expId);
        //页面跳转
        request.setAttribute("exp2List", exp2List);
        return "/expense/expenseDetail.html";
    }

    /**
     * 审核
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/audit")
    public void audit(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //获取参数
        int expId = 0;
        try {
            expId = Integer.parseInt(request.getParameter("expId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            //报销单编号如果得不到，就不能向下执行了，直接报错
            response.getWriter().print("未获取到报销单编号");
        }
        String result = request.getParameter("result");
        String auditDesc = request.getParameter("auditDesc");
        Employee emp = (Employee) request.getSession().getAttribute("user");
        String auditorId = emp.getEmpid();
        //调用业务层完成审核操作
        Auditing auditing = new Auditing(result, auditDesc, expId, auditorId);
        try {
            expService.audit(auditing);
            //添加成功
            response.getWriter().print("审核成功");
        } catch (Exception e) {
            //添加失败
            response.getWriter().print("审核失败");
        }
        //页面跳转
    }

    /**
     * 待审报销
     * @param currnav
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/toAudit")
    public String toAudit(Integer currnav,HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        if(currnav == null){
            currnav=1;
        }
        //获取当前用户的编号
        Employee emp = (Employee) request.getSession().getAttribute("user");
        String empId = emp.getEmpid();
        //获取当前页号
        int pagesize = 10;
        int navnum = 10;//页面导航栏维护的导航数
        //调用业务层返回数据

        //从数据库查询出来数据总行数
        int rowcount = 10;
        String sql = "select count(1) from expense exp join employee emp on exp.empid = emp.empid where nextauditor='"+empId+"'";
        rowcount = DBUtil2.getCount(sql);
        //调用pageUtil工具，获取分页导航栏数据
        PageUtil pageUtil = new PageUtil(rowcount,pagesize,currnav,navnum);
        //查询数据
        int startrow = pageUtil.getStartrow();
        int num = startrow + pagesize;
        int num2 = startrow +1;
        //调用业务层完成查询操作（获取当前用户的待审核报销单）\
        List<Expense> expList = expService.toAudit(empId,num,num2);
        //页面跳转
        request.setAttribute("expList", expList);
        request.setAttribute("pageUtil",pageUtil);
        return "/expense/toAudit.html";
    }

    /**
     * 添加报销
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/add")
    public String add(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {
        //获取视图层的报销单和明细数据
        String[] typeArr = request.getParameterValues("type");
        String[] amountArr = request.getParameterValues("amount");
        String[] itemDescArr = request.getParameterValues("itemDesc");
        double totalAmount = 0;
        List<ExpenseItem> itemList = new ArrayList<ExpenseItem>();
        for (int i = 0; i < typeArr.length; i++) {
            ExpenseItem item = new ExpenseItem();
            item.setType(typeArr[i]);
            item.setAmoumt(Double.parseDouble(amountArr[i]));
            item.setItemDesc(itemDescArr[i]);
            itemList.add(item);
            //计算总金额
            totalAmount += Double.parseDouble(amountArr[i]);
        }
        Date expTime = new Date();
        String expDesc = request.getParameter("expDesc");
        String empId = request.getParameter("empId");
        String nextAuditor = request.getParameter("nextAuditor");
        String lastResult = "新创建的";
        String status = "0";
        //调用业务层完成添加报销单的操作
        //Expense exp = new Expense();
        Expense exp = new Expense(totalAmount, expTime, expDesc, lastResult, status, empId, nextAuditor, itemList,list);
        try {
            //添加成功
            expService.add(exp);
            //添加成功，清空存地址集合
            list.clear();
            return "redirect:/ExpenseController/findById";
        } catch (Exception e) {
            //添加失败
            request.setAttribute("error", "添加失败");
            return "/expense/expenseAdd.html";
        }
        //页面跳转
    }

    /**
     * 图片上传
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/onload")
    public Map<String,Object> MultiPictareaddData(@RequestParam("file") MultipartFile[] file, HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();
        if (file != null && file.length > 0) {
            for (int i = 0; i < file.length; i++) {
                MultipartFile filex = file[i];
                // 保存文件
                saveFile(request, filex);
            }
            map.put("success", "ok");
            map.put("msg", "上传成功");
        } else {
            map.put("msg", "上传失败");
        }
        return map;
    }
    private void saveFile(HttpServletRequest request, MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String name = file.getName();
        String randomUUID = UUID.randomUUID().toString();
        String[] splitUUID = randomUUID.split("-");
        int index = originalFilename.lastIndexOf(".");
        String exet = originalFilename.substring(index);
        String fileName =  splitUUID[new Random().nextInt(5)] + exet;
        String filePath = "/Users/jiangwen/Desktop/oabg/target/classes/static/uploads/";
        File file2 = new File(filePath);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        filePath += fileName;
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/static/uploads/";
        ExpenseImg expenseImg = new ExpenseImg();
        expenseImg.setPhoto(basePath+fileName);
        list.add(expenseImg);
        file.transferTo(new File(filePath));// ctrl+1

    }
}
