package top.oldmoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient // 启用Eureka客户端，注册到Eureka服务
@SpringBootApplication
public class StartProvider8001 {
    public static void main(String[] args) {
        SpringApplication.run(StartProvider8001.class, args);
    }
}
