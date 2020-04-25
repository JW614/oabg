package com.dzxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.dzxy.pojo.Dept;
import com.dzxy.pojo.Duty;
import com.dzxy.pojo.DutyPage;
import com.dzxy.pojo.Employee;
import com.dzxy.service.DeptService;
import com.dzxy.service.DutyService;
import com.dzxy.util.DBUtil2;
import com.dzxy.util.GdUtil;
import com.dzxy.util.PageUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/DutyController")
public class DutyController {
    @Autowired
    DutyService dutyService;
    @Autowired
    DeptService deptService;

    /**
     * 获取当前所在城市
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/ipGd")
    public void ipGd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cip = request.getParameter("cip");
        JSONObject object = GdUtil.getCityCodeByIp(cip);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(object);
    }

    /**
     * 我的考勤
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
        //获取当前页号
        int pagesize = 10;
        int navnum = 10;//页面导航栏维护的导航数
        //调用业务层返回数据

        //从数据库查询出来数据总行数
        int rowcount = 10;
        Employee user = (Employee)request.getSession().getAttribute("user");
        String sql = "select count(1) from duty where emprid='"+user.getEmpid()+"'";
        rowcount = DBUtil2.getCount(sql);
        //调用pageUtil工具，获取分页导航栏数据
        PageUtil pageUtil = new PageUtil(rowcount,pagesize,currnav,navnum);
        //查询数据
        int startrow = pageUtil.getStartrow();
        int num = startrow + pagesize;
        int num2 = startrow +1;
        //获取正在登陆人的ID
        Employee emp = (Employee) request.getSession().getAttribute("user");
        String empid = emp.getEmpid();
        //调用业务层查找符合条件的数据
        List<Duty> dutyList = dutyService.findById(empid,num,num2);
        //跳转到指定页面
        request.setAttribute("dutyList", dutyList);
        request.setAttribute("pageUtil",pageUtil);
        return "/duty/myDuty.html";
    }

    /**
     * 打印
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/exportXls")
    public void exportXls(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        //接受来自于视图层的查询条件
        int deptno = 0;
        try {
            deptno = Integer.parseInt(request.getParameter("deptno"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String empId = request.getParameter("empId");
        String dtDate = request.getParameter("dtDate");
        //调用业务层获取考勤信息
        List<Duty> dutyList = dutyService.find2(deptno, empId, dtDate);
        //导出到xls文档中
        exporXls(dutyList, response);
    }

    private void exporXls(List<Duty> dutyList, HttpServletResponse response) {

        // 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet("考勤信息表");

        /**
         *  可以合并行和列
         */
        CellRangeAddress region = new CellRangeAddress(0, // first row
                0, // last row
                0, // first column
                7 // last column
        );
        sheet.addMergedRegion(region);

        /**
         * 添加表格头
         */
        /**
         *
         * 创建 表的行  第一行 
         */
        HSSFRow hssfRow = sheet.createRow(0);
        /**
         * 创建单元格  第一个单元格
         */
        HSSFCell headCell = hssfRow.createCell(0);
        /**
         * 添加内容
         */
        headCell.setCellValue("                         考勤列表");

        // 设置单元格格式居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);


        // 添加表头行
        hssfRow = sheet.createRow(1);
        // 添加表头内容
        headCell = hssfRow.createCell(0);
        headCell.setCellValue("用户名");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(1);
        headCell.setCellValue("真实姓名");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(2);
        headCell.setCellValue("所属部门");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(3);
        headCell.setCellValue("出勤日期");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(4);
        headCell.setCellValue("签到时间");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(5);
        headCell.setCellValue("签退时间");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(6);
        headCell.setCellValue("签到城市");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(7);
        headCell.setCellValue("签退城市");
        headCell.setCellStyle(cellStyle);

        // 添加数据内容
        for (int i = 0; i < dutyList.size(); i++) {
            hssfRow = sheet.createRow((int) i + 2);
            Duty duty = dutyList.get(i);

            // 创建单元格，并设置值
            HSSFCell cell = hssfRow.createCell(0);
            cell.setCellValue(duty.getEmp().getEmpid());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(1);
            cell.setCellValue(duty.getEmp().getRealName());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(2);
            cell.setCellValue(duty.getEmp().getDept().getDeptname());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(3);
            if (duty.getDtdate() != null) {
                cell.setCellValue(duty.getDtdate());
            } else {
                cell.setCellValue("");
            }

            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(4);
            cell.setCellValue(duty.getSignintime());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(5);
            cell.setCellValue(duty.getSignouttime());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(6);
            cell.setCellValue(duty.getIncity());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(7);
            cell.setCellValue(duty.getOutcity());
            cell.setCellStyle(cellStyle);
        }

        // 保存Excel文件
        try {
            //  OutputStream outputStream = new FileOutputStream("D:/students.xls");

            //在 tomcat/conf/web.xml有 mime类型
            ///设置响应类型
            response.setContentType("application/vnd.ms-excel");
            //设置浏览器添加附件  ，下载到客户端本地
            response.setHeader("Content-Disposition", "attachment;filename=Duty.xls");

            OutputStream outputStream = response.getOutputStream();
            //将 工作薄 放到  输出流中，下载到浏览器客户端
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 条件查询
     * @param currnav
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/find")
    public void find(Integer currnav,HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException {
        //接受来自于视图层的查询条件
        int deptno = 0;
        try {
            deptno = Integer.parseInt(request.getParameter("deptno"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String empId = request.getParameter("empId");
        String dtDate = request.getParameter("dtDate");
        if(currnav == null){
            currnav=1;
        }
        //获取当前页号
        int pagesize = 10;
        int navnum = 10;//页面导航栏维护的导航数
        //调用业务层返回数据

        //从数据库查询出来数据总行数
        int rowcount = 10;
        StringBuilder sql = new StringBuilder("select count(1) from duty dt"
                +" join employee e"
                +" on dt.emprid = e.empid"
                +" join dept d"
                +" on e.deptno = d.deptno where 1=1");
        if (deptno != 0) {
            sql.append(" and d.deptno = " + deptno);
        }
        if (empId != null && !"".equals(empId)) {
            sql.append(" and emprid like '%" + empId + "%'");
        }
        if (dtDate != null && !"".equals(dtDate)) {
            sql.append(" and to_char(dtdate,'YYYY-MM-DD')>'" + dtDate + "'");
        }
        rowcount = DBUtil2.getCount(sql.toString());
        //调用pageUtil工具，获取分页导航栏数据
        PageUtil pageUtil = new PageUtil(rowcount,pagesize,currnav,navnum);
        //查询数据
        int startrow = pageUtil.getStartrow();
        int num = startrow + pagesize;
        int num2 = startrow +1;
        //调用业务层获取考勤信息
        List<Duty> dutyList = dutyService.find(deptno, empId, dtDate,num,num2);
        DutyPage dutyPage = new DutyPage(dutyList, pageUtil);
        //将考勤信息转换成jsonstr直接返回
        response.setContentType("text/html;charset=utf-8");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String jsonStr = gson.toJson(dutyPage);
        response.getWriter().print(jsonStr);
    }

    /**
     * 条件查询-生成部门下拉框
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/findDepts")
    public void findDepts(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        //调用业务层获取部门列表
        List<Dept> deptList = deptService.findAll();
        //直接返回部门列表json字符串
        Gson gson = new Gson();
        String jsonStr = gson.toJson(deptList);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(jsonStr);
    }
    /**
     * 签退
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/signout")
    public void signout(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        //获取当前用户的编号
        Employee emp = (Employee) request.getSession().getAttribute("user");
        String empId = emp.getEmpid();
        String outcity = request.getParameter("outcity");
        //调用业务层完成签到操作
        int n = dutyService.signout(empId, outcity);//0--签退失败   1--签退成功   2--尚未签到
        //返回结果
        response.setContentType("text/html;charset=utf-8");
        String result = "签退失败";
        if (n == 1) {
            result = "签退成功";
        } else if (n == 2) {
            result = "尚未签到";
        }
        response.getWriter().print(result);
    }

    /**
     * 签到
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/signin")
    public void signin(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        //获取当前用户的编号
        Employee emp = (Employee) request.getSession().getAttribute("user");
        String empId = emp.getEmpid();
        String incity = request.getParameter("incity");
        //调用业务层完成签到操作
        int n = dutyService.signin(empId, incity);//0--签到失败   1--签到成功   2--已经签到
        //返回结果
        response.getWriter().print(n);
    }
}
