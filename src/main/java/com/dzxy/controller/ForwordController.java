package com.dzxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwordController {
    @RequestMapping("/top")
    public String top(){
        return "/top.html";
    }
    @RequestMapping("/left")
    public String left(){
        return "/left.html";
    }
    @RequestMapping("/forgetpwd")
    public String forgetpwd(){
        return "/forgetpwd.html";
    }
    @RequestMapping("/myInfo")
    public String myInfo(){
        return "/my/myInfo.html";
    }
    @RequestMapping("/myPwd")
    public String myPwd(){
        return "/my/myPwd.html";
    }
    @RequestMapping("/deptAdd")
    public String deptAdd(){
        return "/system/deptAdd.html";
    }
    @RequestMapping("/positionAdd")
    public String positionAdd(){
        return "/system/positionAdd.html";
    }
    @RequestMapping("/dutyAdd")
    public String dutyAdd(){
        return "/duty/dutyAdd.html";
    }
    @RequestMapping("/dutyList")
    public String dutyList(){
        return "/duty/dutyList.html";
    }

    @RequestMapping("/incomeAdd")
    public String incomeAdd(){
        return "/inout/incomeAdd.html";
    }
    @RequestMapping("/incomeList")
    public String incomeList(){
        return "/inout/incomeList.html";
    }
    @RequestMapping("/incomestatis")
    public String incomestatis(){
        return "/inout/incomestatis.html";
    }
    @RequestMapping("/expenseList")
    public String expenseList(){
        return "/inout/expenseList.html";
    }
    @RequestMapping("/expensestatis")
    public String expensestatis(){
        return "/inout/expensestatis.html";
    }

    @RequestMapping("/audit")
    public String audit(){
        return "/expense/audit.html";
    }
    @RequestMapping("/expenseAdd")
    public String expenseAdd(){
        return "/expense/expenseAdd.html";
    }
}
