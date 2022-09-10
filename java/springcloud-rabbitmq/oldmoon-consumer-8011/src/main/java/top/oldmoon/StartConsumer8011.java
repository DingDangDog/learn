package top.oldmoon;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient // 启用 Eureka
@EnableFeignClients // 启用 Fegin
@SpringBootApplication
public class StartConsumer8011 {
    public static void main(String[] args) {
        SpringApplication.run(StartConsumer8011.class, args);
    }
}
