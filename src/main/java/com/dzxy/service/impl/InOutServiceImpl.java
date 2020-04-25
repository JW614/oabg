package com.dzxy.service.impl;

import com.dzxy.dao.IncomeDao;
import com.dzxy.dao.PaymentDao;
import com.dzxy.pojo.InCome;
import com.dzxy.pojo.Payment;
import com.dzxy.service.InOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InOutServiceImpl implements InOutService {
    @Autowired
    private IncomeDao icDao;

    public String getPieData(String time) {
        //调用Dao层获取List数据
        //IncomeDao icDao = new IncomeDaoImpl();
        List<Object[]> list = icDao.findStatisData2(time);
        //将List数据转换成json字符串

        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Object[] arr = list.get(i);
            if (i != list.size() - 1) {
                builder.append("{\"value\":" + arr[1] + ",\"name\":\"" + arr[0] + "\"},");
            } else {
                builder.append("{\"value\":" + arr[1] + ",\"name\":\"" + arr[0] + "\"}");
            }
        }
        builder.append("]");
        String jsonStr = builder.toString();
        //返回json字符串
        return jsonStr;
    }

    public String getBarData() {
        //调用Dao层获取List数据
        //IncomeDao icDao = new IncomeDaoImpl();
        List<Object[]> list = icDao.findStatisData();
        //将List数据转换成json字符串
        //Gson gson = new Gson();
        //String jsonStr = gson.toJson(list);
        StringBuilder builder1 = new StringBuilder("[");
        StringBuilder builder2 = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Object[] arr = list.get(i);
            if (i == list.size() - 1) {
                builder1.append("\"" + arr[0] + "\"");
                builder2.append(arr[1]);
            } else {
                builder1.append("\"" + arr[0] + "\",");
                builder2.append(arr[1] + ",");
            }
        }
        builder1.append("]");
        builder2.append("]");
        String jsonStr = "[" + builder1.toString() + "," + builder2.toString() + "]";
        //返回json字符串
        return jsonStr;
    }

    public int incomeAdd(InCome inCome) {

        return this.icDao.incomeAdd(inCome);
    }

    public List<InCome> find(String startTime, String endTime, String icType, int num, int num2) {

        return this.icDao.find(startTime, endTime, icType,num,num2);
    }

    public int delete(int icId) {
        return this.icDao.delete(icId);
    }

}
