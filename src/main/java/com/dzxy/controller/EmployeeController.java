package com.dzxy.controller;

import com.dzxy.pojo.Dept;
import com.dzxy.pojo.Employee;
import com.dzxy.pojo.Position;
import com.dzxy.service.DeptService;
import com.dzxy.service.EmployeeService;
import com.dzxy.service.PositionService;
import com.dzxy.util.DBUtil2;
import com.dzxy.util.MD5Util;
import com.dzxy.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/EmployeeController")
public class EmployeeController {
    @Autowired
    EmployeeService empService;
    @Autowired
    DeptService deptService;
    @Autowired
    PositionService positionService;

    /**
     * 添加员工，ajax校验用户名是否可用
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping("/findEmpId")
    public Map<String,Object> findEmpId(String empId){
        Map<String,Object> map = new HashMap<>();
        //调用业务层对数据进行修改
        boolean flag = empService.findEmpId(empId);
        if (flag) {
            map.put("data","1");
        } else {
            map.put("data","2");
        }
        return map;
    }
    /**
     * 忘记密码
     */
    @RequestMapping("/forgetPwd")
    public String forgetPwd(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        //获取信息
        String empId = request.getParameter("empId");
        String idCard = request.getParameter("idCard");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        //调用业务层
        Employee emp = empService.findById(empId);
        if(emp == null){
            request.setAttribute("error", "用户名或身份证不正确");
            return "/forgetpwd.html";
        }else{
            if (idCard == null) {
                idCard = "1";
            }
            //转发
            //身份证校验
            if (idCard.equals(emp.getIdCard())) {
                //密码和确认密码校验
                if (password1.equals(password2)) {
                    //修改密码
                    int n = empService.pwd(empId, password1);
                    if (n > 0) {
                        return "/login.html";
                    } else {
                        request.setAttribute("error", "修改失败");
                        return "/forgetpwd.html";
                    }
                } else {
                    request.setAttribute("error", "密码和确认密码不相同");
                    return "/forgetpwd.html";
                }
            } else {
                request.setAttribute("error", "用户名或身份证不正确");
                return "/forgetpwd.html";
            }
        }
    }

    /**
     * 个人平台-修改密码（判断旧密码是否正确）
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/oldpwd")
    public void oldpwd(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //获取正在登陆人的ID
        Employee emp = (Employee) request.getSession().getAttribute("user");
        String empid = emp.getEmpid();
        //获取表单数据
        String oldpwd = request.getParameter("oldpwd");
        if(!"123456".equals(oldpwd)){
            oldpwd = MD5Util.getMD5(oldpwd);
        }
        //调用业务层对数据进行修改
        boolean flag = empService.exist(empid, oldpwd);
        if (flag) {
            response.getWriter().print("旧密码正确");
        } else {
            response.getWriter().print("旧密码不正确");
        }

    }

    /**
     *个人平台-修改密码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/pwd")
    public void pwd(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //获取正在登陆人的ID
        Employee emp = (Employee) request.getSession().getAttribute("user");
        String empid = emp.getEmpid();
        //获取表单数据
        String oldpwd = request.getParameter("oldpwd");
        String newpwd = request.getParameter("newpwd");
        String ensure = request.getParameter("ensure");
        //调用业务层对数据进行修改
        if(!"123456".equals(oldpwd)){
            oldpwd = MD5Util.getMD5(oldpwd);
        }
        boolean flag = empService.exist(empid, oldpwd);
        int n = 0;
        if (newpwd.equals(ensure) && flag) {
            n = empService.pwd(empid, newpwd);
        } else {
            n = -1;
        }
        if (n > 0) {
            response.getWriter().print("修改成功");
        } else {
            response.getWriter().print("修改失败");
        }

    }

    /**
     *个人平台-我的信息（对手机号、qq、紧急联系人信息修改）
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/modify")
    public String modify(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        //获取正在登陆人的ID
        Employee emp = (Employee) request.getSession().getAttribute("user");
        String empid = emp.getEmpid();
        //获取表单数据
        String phone = request.getParameter("phone");
        String qq = request.getParameter("qq");
        String emerContectPerson = request.getParameter("emerContectPerson");
        //调用业务层对数据进行修改
        Employee emp2 = new Employee(empid, phone, qq, emerContectPerson);
        int n = empService.modify(emp2);
        //页面跳转
        if (n > 0) {
            request.setAttribute("success", "修改成功");
            request.setAttribute("emp2", emp2);
            return "/my/myInfo.html";
        } else {
            request.setAttribute("error", "修改失败");
            request.setAttribute("emp2", emp2);
            return "/my/myInfo.html";
        }
    }

    /**
     *退出登录
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/loginout")
    public String loginout(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        //结束当前session
        request.getSession().invalidate();
        //重定向到登录页面
        return "redirect:/static/login.html";
    }

    /**
     * 登录
     * @param request
     * @param response
     * @param empId
     * @param password
     * @param verifyCode
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request,HttpServletResponse response,String empId,String password,String verifyCode) {

        if (!"123456".equals(password)) {
            password = MD5Util.getMD5(password);
        }
        String randStr = (String) request.getSession().getAttribute("randStr");
        if (verifyCode == null || !verifyCode.equals(randStr)) {
            request.setAttribute("error", "验证码错误");
            return "/login.html";
        }

        //调用业务层判断是否正确
        Employee emp = empService.login(empId, password);
        //页面跳转
        if (emp != null) {
            request.getSession().setAttribute("user", emp);
            return "redirect:/static/main.html";
        } else {
            request.setAttribute("error", "用户名或密码错误");
            return "/login.html";
        }
    }

    /**
     * 员工管理-查看信息
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/check")
    public String check(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //获取要查看的员工ID
        String empId = request.getParameter("empId");
        //调用业务层获取该员工信息
        Employee emp = empService.findById(empId);
        request.setAttribute("emp", emp);
        //获取所有部门并保存在request中
        List<Dept> deptList = deptService.findAll();
        request.setAttribute("deptList", deptList);
        //获取所有岗位并保存在request中
        List<Position> positionList = positionService.findAll();
        request.setAttribute("positionList", positionList);
        //获取所有员工并保存在request中
        List<Employee> mgrList = empService.findByType(2); //1--基层人员   2--各级管理人员  3--管理员
        request.setAttribute("mgrList", mgrList);
        //页面跳转
        return "/system/empInfo.html";
    }

    /**
     * 修改前将信息展示出来，供修改
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        //获取要修改的员工ID
        String empId = request.getParameter("empId");
        //调用业务层获取该员工信息
        Employee emp = empService.findById(empId);
        request.setAttribute("emp", emp);
        //获取所有部门并保存在request中
        List<Dept> deptList = deptService.findAll();
        request.setAttribute("deptList", deptList);
        //获取所有岗位并保存在request中
        List<Position> positionList = positionService.findAll();
        request.setAttribute("positionList", positionList);
        //获取所有员工并保存在request中
        List<Employee> mgrList = empService.findByType(2); //1--基层人员   2--各级管理人员  3--管理员
        request.setAttribute("mgrList", mgrList);
        //页面跳转
        return "/system/empUpdate.html";
    }

    /**
     * 修改员工信息
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/update")
    public String update(HttpServletRequest req,
                       HttpServletResponse resp) throws ServletException, IOException {
        //获取表单数据
        String empid = req.getParameter("empId");
        String password = "123456";
        String realName = req.getParameter("realName");
        String sex = req.getParameter("sex");
        String phone = req.getParameter("phone");
        String qq = req.getParameter("qq");
        String emerContectPerson = req.getParameter("emerContactPerson");
        String idCard = req.getParameter("idCard");
        //日期
        Date birthDate = null, hireDate = null, leaveDate = null;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sbirthDate = req.getParameter("birthDate");
        String shireDate = req.getParameter("hireDate");
        String sleaveDate = req.getParameter("leaveDate");
        try {
            birthDate = sdf.parse(sbirthDate);
            hireDate = sdf.parse(shireDate);
            leaveDate = sdf.parse(sleaveDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //数字
        int onDuty = Integer.parseInt(req.getParameter("onDuty"));
        int empType = Integer.parseInt(req.getParameter("empType"));
        //对象
        int deptno = Integer.parseInt(req.getParameter("deptno"));
        int positionId = Integer.parseInt(req.getParameter("positionId"));
        String mgrId = req.getParameter("mgrId");
        Dept dept = new Dept();
        dept.setDeptno(deptno);
        Position position = new Position();
        position.setPosid(positionId);
        Employee mgr = new Employee();
        mgr.setEmpid(mgrId);
        //调用业务层完成数据添加
        Employee emp = new Employee(empid, password, realName, sex,
                birthDate, hireDate, leaveDate, onDuty,
                empType, phone, qq, emerContectPerson,
                idCard, dept, position, mgr);
        int n = empService.update(emp);
        //页面跳转
        if (n > 0) {
            return "redirect:/EmployeeController/findAll?error="+3;
        } else {
            req.setAttribute("error", "更新用户失败");
            return "redirect:/EmployeeController/toUpdate?empid="+empid;
        }
    }

    /**
     * 删除员工信息
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        //获取要删除的员工编号
        String empId = request.getParameter("empId");


        //调用业务层删除数据
        empService.delete(empId);
        //页面跳转
        return "redirect:/EmployeeController/findAll?error="+4;
    }

    /**
     * 重置密码
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/resetpwd")
    public String resetpwd(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        //获取要重置密码的员工编号
        String empId = request.getParameter("empId");
        //调用业务层重置密码数据
        Employee emp = empService.findById(empId);
        int n = empService.resetpwd(empId);
        //页面跳转
        if (n > 0) {
            return "redirect:/EmployeeController/findAll?error="+0;
        } else {
            return "redirect:/EmployeeController/findAll?error="+1;
        }
    }

    /**
     * 员工管理条件查询
     * @param currnav
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/find")
    public String find(Integer currnav,HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //接受查询条件
        if(currnav == null){
            currnav=1;
        }
        String empId = request.getParameter("empId");
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        int onDuty = Integer.parseInt(request.getParameter("onDuty"));
        String hireDate = request.getParameter("hireDate");
        request.setAttribute("empId", empId);
        request.setAttribute("deptno2", deptno);
        request.setAttribute("onDuty", onDuty);
        request.setAttribute("hireDate", hireDate);
        //调用业务层完成员工查询
        //获取当前页号
        int pagesize = 10;
        int navnum = 10;//页面导航栏维护的导航数
        //调用业务层返回数据

        //从数据库查询出来数据总行数
        int rowcount = 10;
        StringBuilder sql = new StringBuilder("select count(1) from employee e"
                + " join dept d"
                + " on e.deptno = d.deptno"
                + " join position p"
                + " on e.posid = p.posid"
                + " left join employee mgr"
                + " on e.mgrid = mgr.empid where 1=1");
        if (empId != null) {//&& !"".equals(empId2)
            sql.append(" and e.empid like '%" + empId + "%'");
        }
        if (deptno != 0) {
            sql.append(" and d.deptno =" + deptno + "");
        }
        if (onDuty >= 0) {
            sql.append(" and e.onduty = " + onDuty + "");
        }
        if (hireDate != null && !"".equals(hireDate)) {
            sql.append(" and e.hiredate > to_date('" + hireDate + "','YYYY-MM-DD')");
        }
        rowcount = DBUtil2.getCount(sql.toString());
        //调用pageUtil工具，获取分页导航栏数据
        PageUtil pageUtil = new PageUtil(rowcount,pagesize,currnav,navnum);
        //查询数据
        int startrow = pageUtil.getStartrow();
        int num = startrow + pagesize;
        int num2 = startrow +1;
        List<Employee> empList = empService.findAll(num,num2,empId, deptno, onDuty, hireDate);
        request.setAttribute("empList", empList);
        //调用业务层获取所有部门
        List<Dept> deptList = deptService.findAll();
        request.setAttribute("deptList", deptList);
        request.setAttribute("pageUtil",pageUtil);
        //页面跳转
        return "/system/empList.html";
    }

    /**
     * 员工管理查询所有
     * @param currnav
     * @param error
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/findAll")
    public String findAll(Integer currnav, @RequestParam(defaultValue="-1") int error, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(currnav == null){
            currnav=1;
        }
        if(error==0){
            request.setAttribute("error","重置成功");
        }else if(error==1){
            request.setAttribute("error","重置密码失败");
        }else if(error==2){
            request.setAttribute("error","身份验证失败，输入身份证与被重置用户身份证不符，无法修改");
        }else if(error==3){
            request.setAttribute("error","更新成功");
        }else if(error==4){
            request.setAttribute("error","删除成功");
        }
        //调用业务层获取所有部门
        List<Dept> deptList = deptService.findAll();
        request.setAttribute("deptList", deptList);
        //获取当前页号
        int pagesize = 10;
        int navnum = 10;//页面导航栏维护的导航数
        //调用业务层返回数据

        //从数据库查询出来数据总行数
        int rowcount = 10;
        rowcount = DBUtil2.getCount("select count(1) from EMPLOYEE");
        //调用pageUtil工具，获取分页导航栏数据
        PageUtil pageUtil = new PageUtil(rowcount,pagesize,currnav,navnum);
        //查询数据
        int startrow = pageUtil.getStartrow();
        int num = startrow + pagesize;
        int num2 = startrow +1;
        //调用业务层完成员工查询
        List<Employee> empList = empService.findAll(num,num2);
        //页面跳转
        request.setAttribute("empList", empList);
        request.setAttribute("pageUtil",pageUtil);
        request.setAttribute("empId", null);
        request.setAttribute("deptno2", 0);
        request.setAttribute("onDuty", -1);
        request.setAttribute("hireDate", null);
        return "/system/empList.html";
    }

    /**
     * 添加员工将部门、岗位和管理人员提前查询出来
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/toAdd")
    public String toAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取所有部门并保存在request中
        List<Dept> deptList = deptService.findAll();
        request.setAttribute("deptList", deptList);
        //获取所有岗位并保存在request中
        List<Position> positionList = positionService.findAll();
        request.setAttribute("positionList", positionList);
        //获取所有员工并保存在request中
        List<Employee> mgrList = empService.findByType(2); //1--基层人员   2--各级管理人员  3--管理员
        request.setAttribute("mgrList", mgrList);
        //页面跳转
        return "/system/empAdd.html";
    }

    /**
     * 添加员工
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/add")
    public String add(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //获取表单数据
        String empid = req.getParameter("empId");
        String password = "123456";
        String realName = req.getParameter("realName");
        String sex = req.getParameter("sex");
        String phone = req.getParameter("phone");
        String qq = req.getParameter("qq");
        String emerContectPerson = req.getParameter("emerContectPerson");
        String idCard = req.getParameter("idCard");
        //日期
        Date birthDate = null, hireDate = null, leaveDate = null;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sbirthDate = req.getParameter("birthDate");
        String shireDate = req.getParameter("hireDate");
        String sleaveDate = req.getParameter("leaveDate");
        try {
            birthDate = sdf.parse(sbirthDate);
            hireDate = sdf.parse(shireDate);
            leaveDate = sdf.parse(sleaveDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //数字
        int onDuty = Integer.parseInt(req.getParameter("onDuty"));
        int empType = Integer.parseInt(req.getParameter("empType"));
        //对象
        int deptno = Integer.parseInt(req.getParameter("deptno"));
        int positionId = Integer.parseInt(req.getParameter("positionId"));
        String mgrId = req.getParameter("mgrId");
        Dept dept = new Dept();
        dept.setDeptno(deptno);
        Position position = new Position();
        position.setPosid(positionId);
        Employee mgr = new Employee();
        mgr.setEmpid(mgrId);
        //调用业务层完成数据添加
        Employee emp = new Employee(empid, password, realName, sex,
                birthDate, hireDate, leaveDate, onDuty,
                empType, phone, qq, emerContectPerson,
                idCard, dept, position, mgr);
        int n = empService.add(emp);
        //页面跳转
        if (n > 0) {
            return "redirect:/EmployeeController/findAll";
        } else {
            req.setAttribute("error", "添加用户失败");
            return "/system/empAdd.html";
        }
    }
}
