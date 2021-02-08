package com.cpms.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.cpms.config.EmailConfiguration;
import com.cpms.controllers.AdminController;
import com.cpms.pojos.UserAccount;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailService implements IEmailService {

	private Logger logger = LoggerFactory.getLogger(EmailService.class);
	@Autowired
	private JavaMailSenderImpl sender;

	@Autowired
	private EmailConfiguration emailcnfg;

	@Autowired
	private freemarker.template.Configuration configuration;
	
	private static MimeMessageHelper helper;
	private static Template template; 
	private static MimeMessage message;
//	public static int noOfQuickServiceThreads = 20;
//	private ScheduledExecutorService quickService;

	@PostConstruct
	public void configureEmail() throws MessagingException, IOException {
		Properties mailProps = new Properties();
		mailProps.put("mail.smtp.host", emailcnfg.getHost());
		mailProps.put("mail.smtp.port", emailcnfg.getPort());
		mailProps.put("mail.smtp.auth", true);
		mailProps.put("mail.smtp.starttls.enable", emailcnfg.getTlsEnable());
		mailProps.put("mail.smtp.from", emailcnfg.getUsername());
		mailProps.setProperty("mail.smtp.socketFactory.port", "465");
        mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProps.put("mail.smtp.socketFactory.fallback", "false");
		Session mailSession = Session.getInstance(mailProps, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailcnfg.getUsername(), emailcnfg.getPassword());
			}

		});
		sender.setSession(mailSession);
		message = sender.createMimeMessage();
		helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

//		 add attachment
//		helper.addAttachment("cdac_logo.jpeg", new ClassPathResource("cdac_logo.jpeg"));
		template= configuration.getTemplate("email-template.ftl");
//		quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);
		logger.info("Email configuration Intialization complete");
	}

	@Override
	@Async
	public void sendEmail(List<UserAccount> studentUsers) throws MessagingException, IOException, TemplateException, MailSendException 
	{
		
		if(null != template) {
		helper.setSubject("Welcome to Cdac PMS");
		HashMap<String, String> map = new HashMap<>();
//		long currenttime = System.currentTimeMillis() ;
			for (UserAccount studentAcct : studentUsers) {
				map.put("firstname", studentAcct.getFirstName());
				map.put("lastname", studentAcct.getLastName());
				map.put("username", studentAcct.getEmail());
				String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
				helper.setTo(studentAcct.getEmail());
				helper.setText(html, true);
				sender.send(message);
	//			long currenttime = System.currentTimeMillis() ;
//				System.out.println(studentAcct);
//				quickService.submit(new Runnable() {
//					@Override
//					public void run() {
//						try{
//						sender.send(message);
//						}catch(Exception e){
////							logger.error("Exception occur while send a mail : ",e);
//							System.out.println(e);
//						}
//					}
//				});
				
	//			System.out.println(System.currentTimeMillis() - currenttime);
			}
		}
//		System.out.println(System.currentTimeMillis() - currenttime);
	}
}

