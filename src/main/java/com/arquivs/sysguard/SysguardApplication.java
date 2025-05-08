package com.arquivs.sysguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "com.arquivs.sysguard.entity")
@EnableJpaRepositories(basePackages = "com.arquivs.sysguard.repository")
public class SysguardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysguardApplication.class, args);
	}

}
