package com.marcosbarbero;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcos Barbero
 * @since 2017-06-08
 */
@SpringCloudApplication
public class DummyService {

    public static void main(String... args) {
        SpringApplication.run(DummyService.class, args);
    }

    @RestController
    class DummyController {

        @Value("${eureka.instance.metadataMap.zone}")
        private String zone;

        @GetMapping("/zone")
        public String zone() {
            return zone;
        }

    }
}
