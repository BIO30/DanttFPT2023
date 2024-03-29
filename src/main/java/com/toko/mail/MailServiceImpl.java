package com.toko.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.toko.entity.Mailinfo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class MailServiceImpl implements MailerService{
	
	@Autowired
	JavaMailSender sender;
	
	List<Mailinfo> list = new ArrayList<>();

	@Override
	public void send(Mailinfo mail) throws MessagingException {
		// TODO Auto-generated method stub
		MimeMessage message = sender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody(), true);
		helper.setReplyTo(mail.getFrom());
		
		if(mail.getCc() != null && mail.getCc().length > 0) {
			helper.setCc(mail.getCc());
		}
		
		if(mail.getBcc() != null && mail.getBcc().length > 0) {
			helper.setBcc(mail.getBcc());
		}
		
		if(mail.getAttachments() != null && mail.getAttachments().length > 0) {
			for(String path:mail.getAttachments()) {
				File file = new File(path);
				helper.addAttachment(file.getName(), file);
			}
		}
		
		sender.send(message);
	}

	@Override
	public void send(String to, String subject, String body) throws MessagingException {
		// TODO Auto-generated method stub
		this.send(new Mailinfo(to, subject, body));
	}

	@Override
	public void queue(Mailinfo mail) {
		// TODO Auto-generated method stub
		list.add(mail);
	}

	@Override
	public void queue(String to, String subject, String body) {
		// TODO Auto-generated method stub
		queue(new Mailinfo(to, subject, body));
	}
	
	@Scheduled(fixedDelay = 1000)
	public void run() {
		while(!list.isEmpty()) {
			Mailinfo mail = list.remove(0);
			try {
				this.send(mail);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

}
