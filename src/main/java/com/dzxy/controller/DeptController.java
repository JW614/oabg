package com.dzxy.controller;

import com.dzxy.pojo.Dept;
import com.dzxy.service.DeptService;
import com.dzxy.service.impl.DeptServiceImpl;
import com.dzxy.util.DBUtil2;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/DeptController")
public class DeptController{
    @Autowired
    DeptService deptService;

    /**
     * ajax判断部门编号是否可用
     * @param deptno
     * @return
     */
    @ResponseBody
    @RequestMapping("/find")
    public Map<String,Object> find(@RequestParam(defaultValue = "0") int deptno){
        Map<String,Object> map = new HashMap<>();
        //调用业务层查询数据
        Dept dept = deptService.findById(deptno);
        if(dept != null){
            map.put("data","1");
        }else {
            map.put("data","2");
        }
        return map;
    }

    /**
     * 修改数据
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/update")
    public String update(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        //获取表单数据
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        String dname = request.getParameter("dname");
        String location = request.getParameter("location");


        //调用业务层完成表单更新
        Dept dept = new Dept(deptno, dname, location);
        int n = deptService.update(dept);


        //页面跳转

        if (n > 0) {
            return "redirect:/DeptController/findAll";
        } else {
            request.setAttribute("error", "更新失败");
            return "redirect:/DeptController/findById&deptno=" + deptno+"&error="+1;
        }
    }

    /**
     * 修改数据之前的预修改
     * @param error
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/findById")
    public String findById(@RequestParam(defaultValue = "-1") int error, HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        //获取要查询的部门编号
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        if(error==1){
            request.setAttribute("error", "更新失败");
        }
        //调用业务层查询数据
        Dept dept = deptService.findById(deptno);
        //页面跳转
        request.setAttribute("dept", dept);
        return "/system/deptUpdate.html";
    }

    /**
     * 删除数据
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        //获取要删除的部门编号
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        //调用业务层删除数据
        deptService.delete(deptno);
        //页面跳转
        return "redirect:/DeptController/findAll";
    }

    /**
     * 查询所有部门信息
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        //调用业务层返回数据

        //调用业务层获取所有部门数据
        List<Dept> deptList = deptService.findAll();

        //页面跳转
        request.setAttribute("deptList", deptList);
        return "/system/deptList.html";
    }

    /**
     * 添加部门
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/add")
    public String add(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {
        //获取表单数据
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        String dname = request.getParameter("dname");
        String location = request.getParameter("location");
        //调用业务层完成表单添加
        Dept dept = new Dept(deptno, dname, location);
        int n = deptService.add(dept);
        //页面跳转
        if (n > 0) {
            return "redirect:/DeptController/findAll";
        } else {
            request.setAttribute("error", "添加失败");
            return "/system/deptAdd.html";
        }
    }

}
