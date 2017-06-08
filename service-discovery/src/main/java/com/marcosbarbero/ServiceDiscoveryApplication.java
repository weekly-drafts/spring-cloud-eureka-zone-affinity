package com.marcosbarbero;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Marcos Barbero
 * @since 2017-06-08
 */
@EnableEurekaServer
@SpringCloudApplication
public class ServiceDiscoveryApplication {

    public static void main(String... args) {
        SpringApplication.run(ServiceDiscoveryApplication.class, args);
    }
}
