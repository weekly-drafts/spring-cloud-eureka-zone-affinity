package com.marcosbarbero.wd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Marcos Barbero
 */
@EnableEurekaServer
@SpringBootApplication
public class ServiceDiscoveryApplication {

    public static void main(String... args) {
        SpringApplication.run(ServiceDiscoveryApplication.class, args);
    }
}
