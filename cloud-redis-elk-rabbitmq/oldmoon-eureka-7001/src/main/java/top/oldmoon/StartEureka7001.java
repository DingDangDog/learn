package top.oldmoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class StartEureka7001 {
    public static void main(String[] args) {
        SpringApplication.run(StartEureka7001.class,args);
    }
}
