package com.dzxy.test;

public class Test4 {
    public static void main(String[] args) {
        int i = 1;
//        i = i++;
//        System.out.println("i1="+i);
        int j = i++;
        System.out.println("i2="+i);
        System.out.println("j="+j);
        int k = i+ ++i * i++;
        System.out.println("i3="+i);
        System.out.println(j);
        System.out.println(k);
    }
}
