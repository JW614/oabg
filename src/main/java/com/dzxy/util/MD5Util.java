package com.dzxy.util;

import java.security.MessageDigest;

public class MD5Util {
    public final static String getMD5(String s) {
        //十六进制
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            //获得MD5滴要算法的MessageDigest对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新滴要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换成十六进制字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            //循环遍历获取值
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            //返回str
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getMD5("123456pppppppppppppppp").length());
    }
}
