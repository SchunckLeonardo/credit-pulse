package com.creditpulse.creditpulseapi;

import org.springframework.boot.SpringApplication;

public class TestCreditPulseApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(Boot::main).with(TestcontainersConfiguration.class).run(args);
    }

}
