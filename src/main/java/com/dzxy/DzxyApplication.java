package com.dzxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class DzxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DzxyApplication.class, args);
    }

}
