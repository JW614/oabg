package com.dzxy.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "loginFilter",urlPatterns = "*.html")
public class LoginFilter implements Filter {
    //实例化一个静态的集合(这里可以改用map集合，提高性能，每次过滤可以不用for匹配)
    private static List<String> urls = new ArrayList<>();

    //静态代码块中向集合中存放所有可以放行的请求或网页地址（不用账号密码即可访问）
    static {
        urls.add("/login.html");
        urls.add("/login2.html");
    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //得到当前页面所在目录下全名称
        String urlPattern = req.getServletPath();
        //得到页面所在服务器的绝对路径
        String path = req.getRequestURI();
        //System.out.println(urlPattern);
        for (String url : urls) {
            if (url.equals(urlPattern) || path.contains(url)) {
                //        System.out.println("reaource do chain...");
                chain.doFilter(request, response);
                //防止重复响应
                return;
            }
        }
        //如果user为null，表示没有登录
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect("http://localhost:8080/static/login.html");
            return;
        } else {
            //放行
            chain.doFilter(request, response);
        }

    }


}
