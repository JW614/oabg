package com.dzxy.service.impl;

import com.dzxy.dao.*;
import com.dzxy.pojo.*;
import com.dzxy.service.ExpenseService;
import com.dzxy.util.Constants;
import com.dzxy.util.DBUtil2;
import com.dzxy.util.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
@Service
public class ExpenseServiceImpl implements ExpenseService {
    /**
     * 问题1. 报销单明细属于某个报销单，但是报销单的编号expId，意味着写入数据库才知道expId是多少
     * 怎么办
     * <p>
     * 方法1：添加了报销单之后，在查询一下当前表的最大的expid
     * 方法2：添加报销单之前，先获取制定序列的nextval，它就是报销单的expid，也是所有明细所属报销单的expId
     * <p>
     * 问题2. 明明添加报销单和明细失败了，为什么Servlet层执行的成功的代码
     * 因为在DBUtil中出现异常进行捕获，但是没有上报
     * 那就上报吧！！！
     * 因为SQLException是检查异常checked异常，必须处理，会影响所有DAO DML方法的声明
     * 解决方案：创建一个运行时异常对象并抛出
     * <p>
     * 问题3.添加报销单的多个DML要求全部成功或者全部失败
     * 解决方案：将事务从DAO层提到业务层
     */
    @Autowired
    private ExpenseDao expDao;
    @Autowired
    private ExpenseItemDao itemDao;
    @Autowired
    private AuditingDao auditingDao;
    @Autowired
    private PaymentDao pmDao;
    @Autowired
    private EmployeeDao employeeDao;

    public void add(Expense exp) {
        //获取报销单序列的下一个值nextval
        int nextVal = expDao.getNextVal();

        Connection conn = DBUtil2.getConnection();
        //事务手动提交
        try {
            conn.setAutoCommit(false);
            //添加报销主单（一条记录）
            exp.setExpId(nextVal);
            expDao.save(exp);
            List<ExpenseImg> photoList = exp.getPhotoList();
            for (int i = 0;i<photoList.size();i++){
                ExpenseImg expenseImg = photoList.get(i);
                expenseImg.setNextVal(nextVal);
                itemDao.savePhoto(expenseImg);
            }
            //添加该报销主单的多个明细项（多条记录）
            List<ExpenseItem> itemList = exp.getItemList();
            for (int i = 0; i < itemList.size(); i++) {
                ExpenseItem item = itemList.get(i);
                item.setExpId(nextVal);
                itemDao.save(item);
            }
            conn.commit();
        } catch (Exception e) {
            // 处理异常1：显示异常信息
            e.printStackTrace();
            //处理异常2：回滚事务
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            //抛出异常
            throw new MyException(e.getMessage());
        } finally {
            DBUtil2.closeAll(null, null, conn);
        }

    }

    public List<Expense> toAudit(String empId, int num, int num2) {

        return this.expDao.findByEmpId(empId,num,num2);
    }

    public void audit(Auditing auditing) {
        Connection conn = DBUtil2.getConnection();
        //事务手动提交
        try {
            conn.setAutoCommit(false);
            String result = auditing.getResult();
            if ("通过".equals(result)) {//通过
                //int positionId = auditing.getAuditor().getPosition().getPosid();
                String auditorId2 = auditing.getAuditorId();
                Employee auditor = employeeDao.findById(auditorId2);
                int positionId = auditor.getPosition().getPosid();
                if (positionId == 6) {//是财务
                    //添加审核记录（可选）
                    auditingDao.save(auditing);
                    //添加财务支出信息
                    int expId = auditing.getExpId();//得到报销单编号
                    Expense exp2 = expDao.findById(expId);
                    Payment pm = new Payment();
                    pm.setAmount(exp2.getTotalAmount());
                    pm.setAuditEmpId(auditing.getAuditorId());
                    //pm.setExpEmpId(exp2.getEmpId());
                    pm.setExpEmpId(exp2.getEmp().getEmpid());
                    pm.setExpId(auditing.getExpId());

                    pmDao.save(pm);
                    //修改报销单状态
                    Expense exp = new Expense();
                    exp.setExpId(auditing.getExpId());
                    exp.setLastResult(result);
                    exp.setStatus(Constants.EXP_STATUS_CASHED); //5 已打款
                    expDao.update(exp);
                } else {
                    //double totalAmount = auditing.getExp().getTotalAmount();
                    int expId = auditing.getExpId();//得到报销单编号
                    Expense exp2 = expDao.findById(expId);
                    double totalAmount = exp2.getTotalAmount();
                    if (totalAmount > 2500) {//大于2500
                        String auditorId = auditing.getAuditorId();
                        if (Constants.EXP_CEOID.equals(auditorId)) {//是总裁
                            //添加审核记录
                            auditingDao.save(auditing);
                            //修改报销单状态
                            Expense exp = new Expense();
                            exp.setExpId(auditing.getExpId());
                            exp.setNextAuditorId(Constants.EXP_CFOID);//下一个审核人是财务
                            exp.setLastResult(result);
                            exp.setStatus(Constants.EXP_STATUS_PASS); //审核通过
                            expDao.update(exp);
                        } else {//不是总裁
                            //添加审核记录
                            auditingDao.save(auditing);
                            //修改报销单状态
                            Expense exp = new Expense();
                            exp.setExpId(auditing.getExpId());
                            exp.setNextAuditorId(Constants.EXP_CEOID);//下一个审核人是总裁
                            exp.setLastResult(result);
                            exp.setStatus(Constants.EXP_STATUS_AUDITING); //审核中
                            expDao.update(exp);
                        }
                    } else {//小于等于2500
                        //添加审核记录
                        auditingDao.save(auditing);
                        //修改报销单状态
                        Expense exp = new Expense();
                        exp.setExpId(auditing.getExpId());
                        exp.setNextAuditorId(Constants.EXP_CFOID);//下一个审核人是财务
                        exp.setLastResult(result);
                        exp.setStatus(Constants.EXP_STATUS_PASS); //审核通过
                        expDao.update(exp);
                    }
                }
            } else {//不通过
                //添加审核记录
                auditingDao.save(auditing);
                //修改报销单状态
                Expense exp = new Expense();
                exp.setExpId(auditing.getExpId());
                exp.setNextAuditorId(null);
                exp.setLastResult(result);
                if ("拒绝".equals(result)) {
                    exp.setStatus(Constants.EXP_STATUS_REJECT); //审核拒绝
                } else {
                    exp.setStatus(Constants.EXP_STATUS_BACK); //审核打回
                }
                expDao.update(exp);
            }
            conn.commit();
        } catch (Exception e) {
            // 处理异常1：显示异常信息
            e.printStackTrace();
            //处理异常2：回滚事务
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            //抛出异常
            throw new MyException(e.getMessage());
        } finally {
            DBUtil2.closeAll(null, null, conn);
        }
    }

    public List<ExpenseItem> see(int expId) {

        return this.itemDao.see(expId);
    }

    public List<Auditing> check(int expId) {

        return this.auditingDao.check(expId);
    }

    public List<Expense> findById(String empid, int num, int num2) {

        return this.expDao.findById(empid,num,num2);
    }

    public List<Expense> find(String empid, int num, int num2) {

        return this.expDao.find(empid,num,num2);
    }

    @Override
    public List<ExpenseImg> findImg(int expId) {
        return this.itemDao.findImg(expId);
    }

}
