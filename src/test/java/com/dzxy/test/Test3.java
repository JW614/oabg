package com.dzxy.test;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.util.Random;

public class Test3 {
    public static void main(String[] args) {
        for (int i=0;i<20;i++){
            int random = new Random().nextInt(5);
            System.out.println(random);
        }
    }
}
