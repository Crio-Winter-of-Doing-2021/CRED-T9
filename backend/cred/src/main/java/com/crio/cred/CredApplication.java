package com.crio.cred;

import javax.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootApplication
public class CredApplication {

    public static void main(String[] args) {
        SpringApplication.run(CredApplication.class, args);
    }

    /**
     * Shallow etag header filter. With this method the etag header is generated.
     *
     * @return the filter
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
