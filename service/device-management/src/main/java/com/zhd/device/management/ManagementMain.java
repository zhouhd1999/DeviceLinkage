package com.zhd.device.management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@ComponentScan({"com.zhd"})
@EnableFeignClients
@EnableScheduling
public class ManagementMain {
    public static void main(String[] args) {
        SpringApplication.run(ManagementMain.class, args);
    }
}
