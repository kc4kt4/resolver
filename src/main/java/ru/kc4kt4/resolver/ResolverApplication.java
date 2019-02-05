package ru.kc4kt4.resolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.kc4kt4.resolver"})
public class ResolverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResolverApplication.class, args);
    }
}

