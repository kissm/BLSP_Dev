package com.lpcode.modules.service.impl.message.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

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
import com.lpcode.modules.service.message.IClientEmail;

/**
 * @author pengs
 * @Date 2015年9月17日 下午12:36:48
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PropertySources(value = { @PropertySource("classpath:application.properties") })
public class SohuEmailClient implements IClientEmail {
	@Value("${sohu.email.mogubang.username}")
	private String username;
	@Value("${sohu.email.mogubang.password}")
	private String password;
	@Value("${sohu.email.mogubang.smtphost}")
	private String smtphost;
	@Value("${sohu.email.mogubang.sender}")
	private String sender;
	@Value("${sohu.email.mogubang.sendername}")
	private String sendername;
	@Value("${sohu.email.mogubang.apiuserbatch}")
	private String apiuserbatch;

	Authenticator auth;
	Authenticator authBatch;
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
		authBatch = new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(apiuserbatch, password);
			}
		};
	}

	@Override
	public ResultEmail send(List<RequestDTOEmail.RequestEmail> receivers, String subject, String content,
			boolean isHtml, boolean isFile, String filePath) {
		try {
			Session session = null;
			session = Session.getDefaultInstance(props, authBatch);
			// 设置session,和邮件服务器进行通讯。
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject); // 设置邮件主题
			String[] filePashArry = filePath.split("/");
			String fileName = filePashArry[filePashArry.length - 1];
			String savePath = "/tmp/" + fileName;
			File saveFile = new File(savePath);
			if (saveFile.exists()) {
				saveFile.delete();
			}
			boolean downloadFileSuccess = httpDownload(filePath, savePath);
			if (isFile && StringUtils.isNotBlank(filePath) && downloadFileSuccess) {// 是否需要上传附件
				Multipart multipart = new MimeMultipart("alternative");
				// 添加html形式的邮件正文
				BodyPart contentPart = new MimeBodyPart();
				contentPart.setHeader("Content-Type", "text/html;charset=UTF-8");
				contentPart.setHeader("Content-Transfer-Encoding", "base64");
				contentPart.setContent(content, "text/html;charset=UTF-8");
				multipart.addBodyPart(contentPart);
				// 添加附件 ( smtp 方式没法使用文件流 )
				File file = new File(savePath);
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(file);
				attachmentBodyPart.setDataHandler(new DataHandler(source));
				attachmentBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
				multipart.addBodyPart(attachmentBodyPart);
				message.setContent(multipart);
			} else {
				message.setContent(content, "text/html;charset = utf-8");
			}
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

	// 下载网络文件
	public static boolean httpDownload(String httpUrl, String saveFile) {
		int bytesum = 0;
		int byteread = 0;

		URL url = null;
		try {
			url = new URL(httpUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return false;
		}

		try {
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(saveFile);

			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				// System.out.println(bytesum);
				fs.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		String url = "http://apitest.huantaoyou.com/upload/other/2015-11-09/930975e5348645b8a5c10dcaeba152b2.pdf";
		String[] filenames = url.split("/");
		String fileName = filenames[filenames.length - 1];
		System.out.println(fileName);
		String filePath = "/tmp/" + fileName;
		File file = new File(filePath);
		System.out.println(file.getPath());
		if (file.exists()) {
			System.out.println("exists");
		} else {
		}

		httpDownload(url, filePath);
	}
}
