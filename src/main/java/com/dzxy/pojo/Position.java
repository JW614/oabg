/**
 *
 */
package com.dzxy.pojo;

/**
 * @author Administrator
 *
 */
public class Position {
//	POSID NUMBER(5)                               
//	PNAME VARCHAR2(15)  Y                         
//	PDESC VARCHAR2(100) Y  

    private int posid;
    private String pname;
    private String pdesc;

    /**
     * @param posid
     * @param pname
     */
    public Position(int posid, String pname) {
        super();
        this.posid = posid;
        this.pname = pname;
    }

    public Position(int posid, String pname, String pdesc) {
        super();
        this.posid = posid;
        this.pname = pname;
        this.pdesc = pdesc;
    }

    /**
     *
     */
    public Position() {
        super();
    }

    /**
     * @return the posid
     */
    public int getPosid() {
        return posid;
    }

    /**
     * @param posid the posid to set
     */
    public void setPosid(int posid) {
        this.posid = posid;
    }

    /**
     * @return the pname
     */
    public String getPname() {
        return pname;
    }

    /**
     * @param pname the pname to set
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * @return the pdesc
     */
    public String getPdesc() {
        return pdesc;
    }

    /**
     * @param pdesc the pdesc to set
     */
    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Position [posid=" + posid + ", pname=" + pname + ", pdesc="
                + pdesc + "]";
    }


}
