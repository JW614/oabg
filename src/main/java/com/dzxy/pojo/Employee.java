package com.dzxy.pojo;

import java.util.Date;

public class Employee {
    private String empid;  //员工编号
    private String password;  //密码
    private String realName;  //真实姓名
    private String sex;  //性别
    private Date birthDate;  //出生日期
    private Date hireDate;  // 入职日期
    private Date leaveDate;  //离职日期
    private int onDuty;  //是否在职 0---离职  1---在职
    private int empType;  //员工类型  1--普通员工  2--管理人员  含经理、总监、总裁等
    private String phone; // 手机
    private String qq;  // QQ
    private String emerContectPerson;  //紧急联系人
    private String idCard;  // 身份证号

    //	private int deptno;   //所属部门的编号
//	private int positionid;   //所属岗位的编号
//	private String mgrId;  //上级经理的编号
    private Dept dept;  //包含部门所有信息
    private Position position;  //包含岗位所有信息
    private Employee mgr;//直接上级
//	private List<Employee> empList = new ArrayList<Employee>();  //下级存入集合中

    public Employee(String empid, String password, String realName, String sex,
                    Date birthDate, Date hireDate, Date leaveDate, int onDuty, int empType,
                    String phone, String qq, String emerContectPerson, String idCard,
                    Dept dept, Position position, Employee mgr) {
        super();
        this.empid = empid;
        this.password = password;
        this.realName = realName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.leaveDate = leaveDate;
        this.onDuty = onDuty;
        this.empType = empType;
        this.phone = phone;
        this.qq = qq;
        this.emerContectPerson = emerContectPerson;
        this.idCard = idCard;
        this.dept = dept;
        this.position = position;
        this.mgr = mgr;
    }

    public Employee() {

    }

    public Employee(String empid, String realName, String sex, Date hireDate,
                    String phone, Dept dept, Position position, Employee mgr) {
        super();
        this.empid = empid;
        this.realName = realName;
        this.sex = sex;
        this.hireDate = hireDate;
        this.phone = phone;
        this.dept = dept;
        this.position = position;
        this.mgr = mgr;
    }

    public Employee(String empid, String realName) {
        super();
        this.empid = empid;
        this.realName = realName;
    }

    public Employee(String empid, String phone, String qq,
                    String emerContectPerson) {
        super();
        this.empid = empid;
        this.phone = phone;
        this.qq = qq;
        this.emerContectPerson = emerContectPerson;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public int getOnDuty() {
        return onDuty;
    }

    public void setOnDuty(int onDuty) {
        this.onDuty = onDuty;
    }

    public int getEmpType() {
        return empType;
    }

    public void setEmpType(int empType) {
        this.empType = empType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmerContectPerson() {
        return emerContectPerson;
    }

    public void setEmerContectPerson(String emerContectPerson) {
        this.emerContectPerson = emerContectPerson;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Employee getMgr() {
        return mgr;
    }

    public void setMgr(Employee mgr) {
        this.mgr = mgr;
    }

    @Override
    public String toString() {
        return "Employee [empid=" + empid + ", password=" + password
                + ", realName=" + realName + ", sex=" + sex + ", birthDate="
                + birthDate + ", hireDate=" + hireDate + ", leaveDate="
                + leaveDate + ", onDuty=" + onDuty + ", empType=" + empType
                + ", phone=" + phone + ", qq=" + qq + ", emerContectPerson="
                + emerContectPerson + ", idCard=" + idCard + ", dept=" + dept
                + ", position=" + position + ", mgr=" + mgr + "]";
    }

}
