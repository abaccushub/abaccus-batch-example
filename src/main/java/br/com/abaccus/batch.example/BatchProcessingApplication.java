package br.com.abaccus.batch.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.abaccus.batch.example","br.com.abaccus.motor.web"})
public class BatchProcessingApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BatchProcessingApplication.class, args);
    }
}
