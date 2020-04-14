package net.nh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RESTServerApplication {

    private static final Logger LOG = LoggerFactory.getLogger(RESTServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RESTServerApplication.class, args);
        LOG.info("Application started successfully");
    }

}
