package com.zhj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication()
public class CmsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsystemApplication.class, args);
    }

}
