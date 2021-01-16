package com.cpms.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
import com.cpms.pojos.Student;
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
		sender.setHost(emailcnfg.getHost());
		sender.setPort(emailcnfg.getPort());
		sender.setProtocol(emailcnfg.getProtocol());
		sender.setDefaultEncoding(emailcnfg.getEncoding());
		Properties mailProps = new Properties();
		mailProps.put("mail.smtp.auth", true);
		mailProps.put("mail.smtp.starttls.enable", emailcnfg.getTlsEnable());
		mailProps.put("mail.smtp.from", emailcnfg.getUsername());
		Session mailSession = Session.getDefaultInstance(mailProps, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailcnfg.getUsername(), emailcnfg.getPassword());
			}

		});
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		// add attachment
//			helper.addAttachment("cdac_logo.jpeg", new ClassPathResource("cdac_logo.jpeg"));

		Template t = configuration.getTemplate("email-template.ftl");

		for (UserAccount s : studentUsers) {
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, s);

			helper.setTo(s.getEmail());
			helper.setText(html, true);
			helper.setSubject("Welcome to Cdac PMS");
//			helper.setFrom("cdacgroup10.project@gmail.com");
			sender.send(message);
//			res.emailSuccessCount();
		}
	}
}

