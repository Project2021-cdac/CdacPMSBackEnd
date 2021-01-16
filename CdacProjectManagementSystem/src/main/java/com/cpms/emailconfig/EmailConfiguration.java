package com.cpms.emailconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/*
 * spring.mail.default-encoding=UTF-8
spring.mail.host=smtp.gmail.com
spring.mail.username=cdacgroup10.project@gmail.com
spring.mail.password=edugenic10
spring.mail.port=587
spring.mail.protocol=smtp
spring.mail.test-connection=false
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
 */

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
