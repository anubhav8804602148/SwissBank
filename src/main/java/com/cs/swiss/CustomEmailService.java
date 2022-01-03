package com.cs.swiss;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CustomEmailService {
	public static void sendmail(String subject, String recepient, String body) {
		System.out.println("Sending mail to " + recepient + " with subject " + subject + " and body " + body);
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("anubhav8804602148@gmail.com", "lphmgspsyqqwishd");
			}
		});
		try {

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("anubhav8804602148@gmail.com", false));

			
			/*
			 * Sending all mail to my secondary mail id for now
			 * 
			 */
			// msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recepient));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("anubhavsharmarangers@gmail.com"));
			msg.setSubject(subject);
			msg.setContent(body, "text/html");
			Transport.send(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Mail sent to " + recepient + " with subject " + subject + " and body " + body);
	}
}
