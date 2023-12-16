package com.example.bsms;

import com.example.bsms.configurations.SystemConfigs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(SystemConfigs.class)
@EnableJpaAuditing
public class BsmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BsmsApplication.class, args);
	}

}
