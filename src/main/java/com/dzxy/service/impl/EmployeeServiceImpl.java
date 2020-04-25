package com.dzxy.service.impl;

import com.dzxy.dao.EmployeeDao;
import com.dzxy.pojo.Employee;
import com.dzxy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao empDao;

    public int add(Employee emp) {

        return empDao.save(emp);
    }

    public List<Employee> findByType(int type) {

        return this.empDao.findByType(type);
    }

    public List<Employee> findAll(int num, int num2) {
        // TODO Auto-generated method stub
        return this.empDao.findAll(num,num2);
    }

    public List<Employee> findAll(int num,int num2,String empId, int deptno, int onDuty,
                                  String hireDate) {

        return this.empDao.find(num,num2,empId, deptno, onDuty, hireDate);
    }

    public void delete(String empId) {

        this.empDao.delete(empId);
    }

    public Employee findById(String empId) {

        return this.empDao.findById(empId);
    }

    public int update(Employee emp) {

        return this.empDao.update(emp);
    }

    public int resetpwd(String empId) {

        return this.empDao.resetpwd(empId);
    }

    public Employee login(String empId, String password) {
        //方法1：一步到位，创建find(empId,password)这样的方法 如果sql语句使用了拼接，没有使用占位符？就会有sql注入的风险
        //方法2：分成两步，调用findById(empId) 如果用户名找不到，就无需判断密码是否正确，，不管怎样都没有风险
        Employee emp = this.empDao.findById(empId);
        if (emp == null) {
            return null;
        } else if (password.equals(emp.getPassword())) {
            return emp;
        } else {
            return null;
        }
    }

    public int modify(Employee emp2) {

        return this.empDao.modify(emp2);
    }

    public boolean exist(String empid, String oldpwd) {

        return this.empDao.exist(empid, oldpwd);
    }

    public int pwd(String empid, String newpwd) {

        return this.empDao.pwd(empid, newpwd);
    }

    @Override
    public boolean findEmpId(String empId) {
        return this.empDao.findEmpId(empId);
    }

}
