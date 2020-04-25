package com.dzxy.util;

import com.alibaba.fastjson.JSONObject;
import com.oracle.javafx.jmx.json.JSONException;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class GdUtil {
    private static final String KEY = "546bcc891865e528eb3cd7a71d7f7bc1";       //web服务的key

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = JSONObject.parseObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static JSONObject getCityCodeByIp(String ipAddress) {
        JSONObject json = null;
        try {
            json = readJsonFromUrl("http://restapi.amap.com/v3/ip?ip=" + ipAddress + "&key=" + KEY + "");
            if ("0".equals(json.getString("status"))) {      //调用异常时，抛出返回信息，以便分析（我会在错误层统一捕获并输出到error日志）
                throw new MyException("调用高德返回异常: " + json.toJSONString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException("高德获取城市编码：", e);
        }
        return json;
    }

    public static void main(String[] args) {
        JSONObject cityCodeByIp = GdUtil.getCityCodeByIp("192.168.0.176");
        System.out.println(cityCodeByIp);                             //330100

    }
}
