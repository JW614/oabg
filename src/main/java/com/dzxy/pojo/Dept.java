/**
 *
 */
package com.dzxy.pojo;

import java.io.Serializable;

/**
 * @author Administrator
 * Serializable 序列化接口
 *
 */
public class Dept implements Serializable {
    /*	DEPTNO   NUMBER(3)
        DEPTNAME VARCHAR2(15) Y
        LOCATION VARCHAR2(5)  Y */
    private int deptno;
    private String deptname;
    private String location;

    /**
     * <p>Company: bjsxt</p>
     * @author fangyl

     * @date 2018年11月12日

     * @version 1.0

     */
    public Dept(int deptno, String deptname) {
        super();
        this.deptno = deptno;
        this.deptname = deptname;
    }

    public Dept(int deptno, String deptname, String location) {
        super();
        this.deptno = deptno;
        this.deptname = deptname;
        this.location = location;
    }

    /**
     * <p>Company: bjsxt</p>
     * @author fangyl

     * @date 2018年11月12日

     * @version 1.0

     */
    public Dept() {
        super();
    }

    /**
     * @return the deptno
     */
    public int getDeptno() {
        return deptno;
    }

    /**
     * @param deptno the deptno to set
     */
    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    /**
     * @return the deptname
     */
    public String getDeptname() {
        return deptname;
    }

    /**
     * @param deptname the deptname to set
     */
    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Dept [deptno=" + deptno + ", deptname=" + deptname
                + ", location=" + location + "]";
    }

}
