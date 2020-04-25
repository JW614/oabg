package com.dzxy.controller;

import com.dzxy.aiutil.FaceMatch;
import com.dzxy.aiutil.FaceSearch;
import com.dzxy.pojo.Employee;
import com.dzxy.service.AiLoginService;
import com.dzxy.service.impl.AiLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
@RequestMapping("/AiLoginController")
public class AiLoginController {
    @Autowired
    AiLoginService aiLoginService;

    /**
     * 将对比人脸库的json结果返回给前端
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message");
        String search = FaceSearch.search(message);
        FaceMatch faceMatch = new FaceMatch();
        //String search = faceMatch.match(message);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(search);
    }

    /**
     *json比对相似度>80则开始真正的登陆操作
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/login2")
    public void login2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        String empId = request.getParameter("username");
        //调用业务层
        Employee emp = aiLoginService.login(empId);
        //跳转
        if (emp != null) {
            request.getSession().setAttribute("user", emp);
            response.sendRedirect( "http://localhost:8080/static/main.html");
        }
    }
}
