package com.marcosbarbero.wd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Marcos Barbero
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SimpleService {

    public static void main(String... args) {
        SpringApplication.run(SimpleService.class, args);
    }

    @RestController
    class SimpleController {

        @Value("${eureka.instance.metadataMap.zone}")
        private String zone;

        @GetMapping(value = "/zone", produces = APPLICATION_JSON_UTF8_VALUE)
        public String zone() {
            return "{\"zone\"=\"" + zone + "\"}";
        }

    }
}
