package com.arquivs.sysguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SysguardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysguardApplication.class, args);
	}

}
