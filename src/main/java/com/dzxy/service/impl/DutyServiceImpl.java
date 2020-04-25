package com.dzxy.service.impl;

import com.dzxy.dao.DutyDao;
import com.dzxy.pojo.Duty;
import com.dzxy.service.DutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class DutyServiceImpl implements DutyService {
    @Autowired
    private DutyDao dutyDao;

    public int signin(String empId, String incity) {
        //判断用户是否已经签到
        Date now = new Date();
        boolean flag = dutyDao.find(empId, now);  //true--代表签到  false--代表没有签到
        if (flag) {
            return 2;
        } else {
            Duty duty = new Duty();
            duty.setDtdate(new java.sql.Date(now.getTime()));
            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            String singinTime = sdf.format(now);
            duty.setSignintime(singinTime);
            duty.setEmpId(empId);
            duty.setIncity(incity);
            int n = dutyDao.save(duty);
            return n;
        }
    }

    public int signout(String empId, String outcity) {
        //判断用户是否已经签到
        Date now = new Date();
        boolean flag = dutyDao.find(empId, now);  //true--代表签到  false--代表没有签到
        if (!flag) {
            return 2;//尚未签到
        } else {
            Duty duty = new Duty();
            duty.setDtdate(new java.sql.Date(now.getTime()));
            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            String singoutTime = sdf.format(now);
            duty.setSignouttime(singoutTime);
            duty.setEmpId(empId);
            duty.setOutcity(outcity);
            int n = dutyDao.update(duty);
            return n;
        }
    }

    public List<Duty> find(int deptno, String empId, String dtDate, int num, int num2) {

        return this.dutyDao.find(deptno, empId, dtDate,num,num2);
    }

    public List<Duty> findById(String empid, int num, int num2) {

        return this.dutyDao.findById(empid,num,num2);
    }

    @Override
    public List<Duty> find2(int deptno, String empId, String dtDate) {
        return this.dutyDao.find2(deptno, empId, dtDate);
    }

}
