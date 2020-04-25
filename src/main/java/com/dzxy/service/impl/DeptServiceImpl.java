package com.dzxy.service.impl;

import com.dzxy.dao.DeptDao;
import com.dzxy.pojo.Dept;
import com.dzxy.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    public int add(Dept dept) {

        return deptDao.save(dept);
    }

    public List<Dept> findAll() {

        return this.deptDao.findAll();
    }

    public int delete(int deptno) {
        return this.deptDao.delete(deptno);
    }

    public Dept findById(int deptno) {

        return this.deptDao.findById(deptno);
    }

    public int update(Dept dept) {

        return this.deptDao.update(dept);
    }

}
