package net.nh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoapApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SoapApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SoapApplication.class, args);
        LOG.info("Application started successfully");
    }

}
