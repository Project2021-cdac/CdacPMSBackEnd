package com.cpms.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.cpms.emailconfig.EmailConfiguration;
import com.cpms.pojos.UserAccount;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailService implements IEmailService {

	@Autowired
	private JavaMailSenderImpl sender;

	@Autowired
	private EmailConfiguration emailcnfg;

	@Autowired
	private freemarker.template.Configuration configuration;

	@Override
	public void sendEmail(List<UserAccount> studentUsers) throws MessagingException, IOException, TemplateException, MailSendException 
	{
//		sender.setProtocol(emailcnfg.getProtocol());
//		sender.setDefaultEncoding(emailcnfg.getEncoding());
		Properties mailProps = new Properties();
		mailProps.put("mail.smtp.host", emailcnfg.getHost());
		mailProps.put("mail.smtp.port", emailcnfg.getPort());
		mailProps.put("mail.smtp.auth", true);
		mailProps.put("mail.smtp.starttls.enable", emailcnfg.getTlsEnable());
		mailProps.put("mail.smtp.from", emailcnfg.getUsername());
		mailProps.setProperty("mail.smtp.socketFactory.port", "465");
        mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProps.put("mail.smtp.socketFactory.fallback", "false");
		Session mailSession = Session.getDefaultInstance(mailProps, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailcnfg.getUsername(), emailcnfg.getPassword());
			}

		});
		sender.setSession(mailSession);
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		// add attachment
//			helper.addAttachment("cdac_logo.jpeg", new ClassPathResource("cdac_logo.jpeg"));

		Template t = configuration.getTemplate("email-template.ftl");
		HashMap<String, String> map = new HashMap<>();
		for (UserAccount studentAcct : studentUsers) {
			map.put("firstname", studentAcct.getFirstName());
			map.put("lastname", studentAcct.getLastName());
			map.put("username", studentAcct.getEmail());
			map.put("password", studentAcct.getPassword());
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);

			helper.setTo(studentAcct.getEmail());
			helper.setText(html, true);
			helper.setSubject("Welcome to Cdac PMS");
//			helper.setFrom("cdacgroup10.project@gmail.com");
			sender.send(message);
//			res.emailSuccessCount();
		}
	}
}

