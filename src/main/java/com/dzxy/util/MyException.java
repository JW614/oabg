package com.dzxy.util;

public class MyException extends RuntimeException {
    public MyException() {
        super();
    }

    public MyException(String msg) {
        super(msg);
    }

    public MyException(String msg, Exception e) {
        super(msg, e);
    }
}
