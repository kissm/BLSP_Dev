package com.lpcode.modules.service.impl.message.client;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.lpcode.modules.blsp.constdefine.ConstDefine;
import com.lpcode.modules.dto.message.RequestDTOEmail;
import com.lpcode.modules.dto.message.ResultEmail;

/**
 * Package: com.lpcode.service.impl.message.client<br/>
 * Date: 15-6-3<br/>
 * Time: 下午2:12<br/>
 *
 * @author pengs
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PropertySources(value = { @PropertySource("classpath:application.properties") })
public class EmailClient {
	@Value("${email.mogubang.username}")
	private String username;
	@Value("${email.mogubang.password}")
	private String password;
	@Value("${email.mogubang.smtphost}")
	private String smtphost;
	@Value("${email.mogubang.sender}")
	private String sender;
	@Value("${email.mogubang.sendername}")
	private String sendername;

	Authenticator auth;
	Properties props = new Properties();

	@PostConstruct
	public void init() {
		props.setProperty("mail.smtp.host", smtphost);
		props.setProperty("mail.smtp.auth", "true");
		auth = new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
	}

	// @Override
	public ResultEmail send(List<RequestDTOEmail.RequestEmail> receivers, String subject, String content,
			boolean isHtml) {
		try {
			Session session = Session.getDefaultInstance(props, auth);
			// 设置session,和邮件服务器进行通讯。
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject); // 设置邮件主题
			// if(isHtml)
			message.setContent(content, "text/html;charset = utf-8");
			// else message.setText(content); // 设置邮件正文

			// message.setHeader(mail_head_name, mail_head_value); // 设置邮件标题
			message.setSentDate(new Date()); // 设置邮件发送日期
			Address address = new InternetAddress(sender, sendername);
			message.setFrom(address); // 设置邮件发送者的地址
			for (RequestDTOEmail.RequestEmail receiver : receivers) {
				ResultEmail resultEmail = send(receiver, message);
				if (!resultEmail.isSuccess()) {
					return resultEmail;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String msg = ex.getCause().toString();
			if (StringUtils.isBlank(msg)) {
				msg = ex.getMessage();
			}
			if (StringUtils.isBlank(msg)) {
				msg = ConstDefine.MSG_EMAIL_EXCEPTION;
			}
			return new ResultEmail(ConstDefine.CODE_EMAIL_EXCEPTION, msg, sender, sendername);
		}
		return new ResultEmail(sender, sendername);
	}

	public ResultEmail send(RequestDTOEmail.RequestEmail receiver, MimeMessage message) {
		try {
			Address[] to = address(receiver.getEmails());
			if (null != to) {
				message.setRecipients(Message.RecipientType.TO, to);
			}
			Address[] bcc = address(receiver.getBcc());
			if (null != bcc) {
				message.setRecipients(Message.RecipientType.BCC, bcc);
			}
			Address[] cc = address(receiver.getCc());
			if (null != cc) {
				message.setRecipients(Message.RecipientType.CC, cc);
			}
			Transport.send(message); // 发送邮件
		} catch (Exception ex) {
			String msg = ex.getCause().toString();
			if (StringUtils.isBlank(msg)) {
				msg = ex.getMessage();
			}
			if (StringUtils.isBlank(msg)) {
				msg = ConstDefine.MSG_EMAIL_EXCEPTION;
			}
			return new ResultEmail(ConstDefine.CODE_EMAIL_EXCEPTION, msg, sender, sendername);
		}
		return new ResultEmail();
	}

	public Address[] address(List<String> address) throws AddressException {
		if (null == address || 0 == address.size()) {
			return null;
		}
		int size = address.size();
		Address[] receivers = new Address[size];
		for (int i = 0; i < size; i++) {
			receivers[i] = new InternetAddress(address.get(i));
		}
		return receivers;
	}

}
