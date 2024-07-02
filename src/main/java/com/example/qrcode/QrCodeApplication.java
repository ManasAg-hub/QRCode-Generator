package com.example.qrcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QrCodeApplication {

    public static void main(String[] args) {
        System.out.println("The application is running");
        SpringApplication.run(QrCodeApplication.class, args);
    }

}
