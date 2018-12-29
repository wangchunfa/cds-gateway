package net.cds.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableHystrixDashboard
public class GatewayApplication {


    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}

