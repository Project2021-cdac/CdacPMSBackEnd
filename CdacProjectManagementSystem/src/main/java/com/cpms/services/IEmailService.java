package com.cpms.services;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.mail.MailSendException;

import com.cpms.pojos.UserAccount;

import freemarker.template.TemplateException;

public interface IEmailService {
	
	
	void configureEmail() throws MessagingException, IOException;
	void sendEmail(List<UserAccount> studentusers) throws MessagingException, IOException, TemplateException, MailSendException;
}
