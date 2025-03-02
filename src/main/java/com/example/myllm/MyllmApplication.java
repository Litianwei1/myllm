package com.example.myllm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {
		"com.example.myllm"
})
public class MyllmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyllmApplication.class, args);
	}

}
