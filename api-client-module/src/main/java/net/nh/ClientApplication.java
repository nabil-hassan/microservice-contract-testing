package net.nh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        LOG.info("Application started successfully");
    }

}
