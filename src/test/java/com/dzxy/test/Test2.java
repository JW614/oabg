package com.dzxy.test;

public class Test2 {
    public static void main(String[] args) {
        String url = "/Users/jiangwen/Desktop/oabg/src/main/resources/static/uploads/\\32143234.jpg";
        String str1=url.substring(0, url.indexOf("\\"));
        String str2=url.substring(str1.length(), url.length());
        System.out.println(str2);
    }
}
