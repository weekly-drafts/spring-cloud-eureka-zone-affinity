Spring Cloud Eureka - Zone Affinity
---

This is a sample to guide developers into zone affinity configuration for Spring 
Cloud Eureka.  
In this project you gonna find 3 modules:
 
 * service-discovery
   * Eureka Server discovery and registration
 * gateway
   * Zuul API Gateway
 * dummy-service
   * Regular spring-boot rest service
   
### Configuration
Each module has 3 configuration files:
   * application.yml
     * default configuration
   * application-zone1.yml
     * configuration for zone1
   * application-zone2.yml
     * configuration for zone2
     
#### Sample configuration
On `application.yml` of any service you will need common configuration for `eureka.client`

```
 eureka:
   client:
     preferSameZoneEureka: true #keeps request in the same zone
     region: region-1 #label with region name
     serviceUrl: #map of zones and urls
       zone1: http://localhost:8761/eureka/
       zone2: http://127.0.0.1:8762/eureka/
```

On `application-zone*.yml` of any service you will need to configure what's the preferred zone for each profile.
```
eureka:
  instance:
    metadataMap.zone: zone1
  client:
    availabilityZones:
      region-1: zone1,zone2
```

On `application-zone*.yml` of your **Eureka Server** you will need to specify `eureka.client.service-url.defaultZone` 
pointing to each other server making the **Eureka Server** aware of each zone. 

```
eureka:
  instance:
    hostname: localhost #only necessary for localhost
    metadataMap.zone: zone1
  client:
    service-url:
      defaultZone: http://127.0.0.1:8762/eureka/
    availabilityZones:
      region-1: zone1,zone2,defaultZone
```


### Build & Run - Step by step
>Prior to run this project you will need to have Maven configured in your local machine.  

Run the following command line on project root.
```
$ mvn clean package
```

If everything goes ok you will see an output similar to this:

```
 [INFO] Reactor Summary:
 [INFO] 
 [INFO] eureka-zone-affinity ............................... SUCCESS [  0.651 s]
 [INFO] dummy-service ...................................... SUCCESS [  1.190 s]
 [INFO] gateway ............................................ SUCCESS [  0.396 s]
 [INFO] service-discovery .................................. SUCCESS [  0.408 s]
 [INFO] ------------------------------------------------------------------------
 [INFO] BUILD SUCCESS
 [INFO] ------------------------------------------------------------------------
 [INFO] Total time: 3.045 s
 [INFO] Finished at: 2017-06-08T21:59:31+02:00
 [INFO] Final Memory: 44M/519M
 [INFO] ------------------------------------------------------------------------
```

After that you just need to run each service twice, each time on it's on profile. Currently all the modules have 2 
profiles configured `zone1` (default) and `zone2` so in order to make them up and running just execute in the following 
order:

1st - Eureka Server
```
 $ java -jar service-discovery/target/*.jar
 $ java -jar service-discovery/target/*.jar --spring.profiles.active=zone2 
```

2nd - Gateway
```
 $ java -jar gateway/target/*.jar
 $ java -jar gateway/target/*.jar --spring.profiles.active=zone2 
```

3rd - Dummy Service
```
 $ java -jar dummy/target/*.jar
 $ java -jar dummy/target/*.jar --spring.profiles.active=zone2 
```

#### Validation
What differs each zone on every application is the `server.port` where it is running, herewith a list for all of them:
  * Gateway
    * zone1: 8080
    * zone2: 8081
  * Service Discovery
    * zone1: 8761
    * zone2: 8762
  * Dummy
    * zone1: 8181
    * zone2: 8182

In order to validate the zone affinity you just need to make simple requests to dummy service `/zone` endpoint through
each API Gateway e.g:

```
 $ curl http://localhost:8080/dummy/zone
```

This will give a String response with the current zone for dummy service, in this case it will be `zone1`. If you request
the same endpoint for the gateway on `zone` i.e on port `8081` the response will be `zone2` always picking up servers in
the same zone.  

To validate the failover between zones you just need to stop one of the instances and make a request to the opposite 
zone e.g:

1st - Stop `dummy` service on zone1
2nd - Make a request to dummy service through gateway on zone1
```
 $ curl http://localhost:8080/dummy/zone
```
The expected result now will be a String containing `zone2`. Once the `dummy` service for `zone1` is up, running and 
registered on Eureka Server the same curl has to respond `zone1` again.