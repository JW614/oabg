package com.dzxy.controller;

import com.dzxy.pojo.Position;
import com.dzxy.service.PositionService;
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
@RequestMapping("/PositionController")
public class PositionController {
    @Autowired
    PositionService positionService;

    /**
     * 查看岗位编号是否可用
     * @param posid
     * @return
     */
    @ResponseBody
    @RequestMapping("/find")
    public Map<String,Object> find(@RequestParam(defaultValue = "0") int posid){
        Map<String,Object> map = new HashMap<>();
        //调用业务层查询数据
        Position position = positionService.findById(posid);
        if(position != null){
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
        int posid = Integer.parseInt(request.getParameter("posid"));
        String pname = request.getParameter("pname");
        String pdesc = request.getParameter("pdesc");

        //调用业务层完成表单更新
        Position position = new Position(posid, pname, pdesc);
        int n = positionService.update(position);
        //页面跳转
        if (n > 0) {
            return "redirect:/PositionController/findAll";
        } else {
            request.getRequestDispatcher("/servlet/PositionServlet?method=findById&posid=" + posid).forward(request, response);
            return "redirect:/PositionController/findById?posid=" + posid+"&error="+1;
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
    public String findById(@RequestParam(defaultValue = "-1") int error,HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        //获取要查询的岗位编号
        int posid = Integer.parseInt(request.getParameter("posid"));
        if(error==1){
            request.setAttribute("error", "更新失败");
        }
        //调用业务层查询数据
        Position position = positionService.findById(posid);
        //页面跳转
        request.setAttribute("position", position);
        return "/system/positionUpdate.html";
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
        //获取要删除的岗位编号
        int posid = Integer.parseInt(request.getParameter("posid"));

        //调用业务层删除数据
        positionService.delete(posid);
        //页面跳转
        return "redirect:/PositionController/findAll";
    }

    /**
     * 查询所有岗位信息
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        //调用业务层获取所有岗位数据
        List<Position> positionList = positionService.findAll();

        //页面跳转
        request.setAttribute("positionList", positionList);
        return "/system/positionList.html";
    }

    /**
     * 添加岗位
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
        int posid = Integer.parseInt(request.getParameter("posid"));
        String pname = request.getParameter("pname");
        String pdesc = request.getParameter("pdesc");

        //调用业务层完成表单添加
        Position position = new Position(posid, pname, pdesc);
        int n = positionService.add(position);

        //页面跳转
        if (n > 0) {
            return "redirect:/PositionController/findAll";
        } else {
            request.setAttribute("error", "添加失败");
            return "/system/positionAdd.html";
        }
    }

}
