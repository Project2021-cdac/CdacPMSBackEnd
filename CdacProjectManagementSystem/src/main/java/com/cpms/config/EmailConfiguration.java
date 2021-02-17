package com.cpms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


@Component
@Getter
@Setter
public class EmailConfiguration {
	
	@Value("${spring.mail.default-encoding}")	
	private String encoding;
	@Value("${spring.mail.host}")
	private String host;
	@Value("${spring.mail.username}")
	private String username;
	@Value("${spring.mail.password}")
	private String password;
	@Value("${spring.mail.port}")
	private int port;
	@Value("${spring.mail.protocol}")
	private String protocol;
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean authentication;
	@Value("{spring.mail.properties.mail.smtp.starttls.enable}")
	private String tlsEnable;
	
}
