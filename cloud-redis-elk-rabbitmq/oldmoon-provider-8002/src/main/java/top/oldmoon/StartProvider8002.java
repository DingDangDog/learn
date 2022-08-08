package top.oldmoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class StartProvider8002 {
    public static void main(String[] args) {
        SpringApplication.run(StartProvider8002.class, args);
    }
}
