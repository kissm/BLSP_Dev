package com.lpcode.modules.service.impl.message.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import com.lpcode.modules.dto.message.ResultSMS;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PropertySource(value = { "classpath:config/message-setting.properties" })
public class GuoDuSMSClient {
	@Value("${sms.guodu.username}")
	private String username;// = "zitui";
	@Value("${sms.guodu.password}")
	private String password;// = "zitui123";
	@Value("${sms.guodu.sendurl}")
	private String sendurl;// =
							// "http://221.179.180.158:9007/QxtSms/QxtFirewall";

	private String cnname = "";

	public boolean sendSmsMessage(String msg, String mphone) {
		String SendTime = "";
		String AppendID = "";
		String Content_Type = "8";
		String Content = msg + cnname;
		String ValidTime = "";
		String DesMobile[] = { mphone };

		/* post方式发送消息 */
		String postResponse = postSendMsg(username, password, Content, DesMobile, AppendID, SendTime, ValidTime,
				Content_Type);
		Response response = readResponse(postResponse);
		String code = response.getCode();
		System.out.println(postResponse);
		if ("03".equals(code.trim())) {
			return true;
		}
		return false;
	}

	/** post方式 发送消息 */
	/**
	 * @param OperID
	 *            用户名
	 * @param OperPass
	 *            密码
	 * @param Content
	 *            发送内容文字 长度最好不要超过500个字符。
	 * @param DesMobiles
	 *            [] 需要发送的手机号字符串数组 手机号个数请不要超过200个。
	 * @param AppendID
	 *            用户自扩展的号码 。
	 *            若扩展请填写号码，若不扩展请填写"",注意！通道号+国都用户身份号+AppendID总长不能超过20位。
	 *            否则将发送失败。具体号码定义，请参见国都资信通平台接口文档
	 * @param SendTime
	 *            发送时间 如果为定时消息。请填写，格式为yyyyMMddhhmmss 若为实时消息，请填"";
	 * @param ValidTime
	 *            消息有效期 应该大于SendTime，最好不要填写，国都默认消息有效期为SendTime+3。
	 *            如果填写错误容易导致消息过有效期无法发送。
	 * @param Content_type
	 *            内容类型 15为短短信，8为长短信 国都服务端将会自动识别短信长短，所以发送时填写8即可。若填写15 长短信将无法发送。
	 * @return rec_string 国都返回的XML格式的串
	 * @catch Exception
	 */
	public String postSendMsg(String OperID, String OperPass, String Content, String DesMobiles[], String AppendID,
			String SendTime, String ValidTime, String Content_type) {
		/* 将内容用URLEncoder编一次GBK */
		String EncoderContent = "";
		try {
			EncoderContent = URLEncoder.encode(Content, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* 将手机号从数组转变成一个用逗号分开的字符串，字符串末尾有逗号不影响消息下发。 */
		String DesMobile = "";
		for (String desMobile2 : DesMobiles) {
			DesMobile = desMobile2 + "," + DesMobile;
		}
		/* url地址 */
		// String URL = "http://221.179.180.158:9007/QxtSms/QxtFirewall";

		/* 消息参数 */
		String str = "OperID=" + OperID + "&OperPass=" + OperPass + "&SendTime=" + SendTime + "&ValidTime=" + ValidTime
				+ "&AppendID=" + AppendID + "&DesMobile=" + DesMobile.trim() + "&Content=" + EncoderContent
				+ "&ContentType=" + Content_type;

		System.out.println("发送的内容为：" + str);
		/* 使用post方式发送消息 */
		String response = postURL(str, sendurl);

		/* 返回响应 */
		return response;
	}

	/** get方式 发送消息，与post格式完全相同，仅调用发送方法不同this.getURL(str, URL); */
	/**
	 * @param OperID
	 *            用户名
	 * @param OperPass
	 *            密码
	 * @param Content
	 *            发送内容文字 长度最好不要超过500个字符。
	 * @param DesMobiles
	 *            [] 需要发送的手机号字符串数组 手机号个数请不要超过200个。
	 * @param AppendID
	 *            用户自扩展的号码 。
	 *            若扩展请填写号码，若不扩展请填写"",注意！通道号+国都用户身份号+AppendID总长不能超过20位。
	 *            否则将发送失败。具体号码定义，请参见国都资信通平台接口文档
	 * @param SendTime
	 *            发送时间 如果为定时消息。请填写，格式为yyyyMMddhhmmss 若为实时消息，请填"";
	 * @param ValidTime
	 *            消息有效期 应该大于SendTime，最好不要填写，国都默认消息有效期为SendTime+3。
	 *            如果填写错误容易导致消息过有效期无法发送。
	 * @param Content_type
	 *            内容类型 15为短短信，8为长短信 国都服务端将会自动识别短信长短，所以发送时填写8即可。若填写15 长短信将无法发送。
	 * @return rec_string 国都返回的XML格式的串
	 * @catch Exception
	 */
	public String getSendMsg(String OperID, String OperPass, String Content, String DesMobiles[], String AppendID,
			String SendTime, String ValidTime, String Content_type) {
		/* 将内容用URLEncoder编一次GBK */
		String EncoderContent = "";
		try {
			EncoderContent = URLEncoder.encode(Content, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* 将手机号从数组转变成一个用逗号分开的字符串，字符串末尾有逗号不影响消息下发。 */
		String DesMobile = "";
		for (String desMobile2 : DesMobiles) {
			DesMobile = desMobile2 + "," + DesMobile;
		}
		/* url地址 */
		// String URL = "http://221.179.180.158:9007/QxtSms/QxtFirewall";

		/* 消息参数 */
		String str = "OperID=" + OperID + "&OperPass=" + OperPass + "&SendTime=" + SendTime + "&ValidTime=" + ValidTime
				+ "&AppendID=" + AppendID + "&DesMobile=" + DesMobile.trim() + "&Content=" + EncoderContent
				+ "&ContentType=" + Content_type;

		System.out.println("发送的内容为：" + str);
		/* 使用get方式发送消息 */
		String response = getURL(str, sendurl);

		/* 返回响应 */
		return response;
	}

	/** post方式 发送url串 */
	/**
	 * @param commString
	 *            需要发送的url参数串
	 * @param address
	 *            需要发送的url地址
	 * @return rec_string 国都返回的XML格式的串
	 * @catch Exception
	 */
	public String postURL(String commString, String address) {
		String rec_string = "";
		URL url = null;
		HttpURLConnection urlConn = null;
		try {
			/* 得到url地址的URL类 */
			url = new URL(address);
			/* 获得打开需要发送的url连接 */
			urlConn = (HttpURLConnection) url.openConnection();
			/* 设置连接超时时间 */
			urlConn.setConnectTimeout(30000);
			/* 设置读取响应超时时间 */
			urlConn.setReadTimeout(30000);
			/* 设置post发送方式 */
			urlConn.setRequestMethod("POST");
			/* 发送commString */
			urlConn.setDoOutput(true);
			urlConn.setDoInput(true);
			OutputStream out = urlConn.getOutputStream();
			out.write(commString.getBytes());
			out.flush();
			out.close();
			/* 发送完毕 获取返回流，解析流数据 */
			BufferedReader rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "GBK"));
			StringBuffer sb = new StringBuffer();
			int ch;
			while ((ch = rd.read()) > -1) {
				sb.append((char) ch);
			}
			rec_string = sb.toString().trim();
			/* 解析完毕关闭输入流 */
			rd.close();
		} catch (Exception e) {
			/* 异常处理 */
			rec_string = "-107";
			System.out.println(e);
		} finally {
			if (urlConn != null) {
				/* 关闭URL连接 */
				urlConn.disconnect();
			}
		}
		/* 返回响应内容 */
		return rec_string;
	}

	/** get方式 发送url串,与post方式代码基本相同，仅发送方式不同 */
	/**
	 * @param commString
	 *            需要发送的url参数串
	 * @param address
	 *            需要发送的url地址
	 * @return rec_string 国都返回的XML格式的串
	 * @catch Exception
	 */
	public String getURL(String commString, String address) {
		String rec_string = "";
		URL url = null;
		HttpURLConnection urlConn = null;
		try {
			/* 得到url地址的URL类 */
			url = new URL(address + "?" + commString);
			/* 获得打开需要发送的url连接 */
			urlConn = (HttpURLConnection) url.openConnection();
			/* 设置连接超时时间 */
			urlConn.setConnectTimeout(30000);
			/* 设置读取响应超时时间 */
			urlConn.setReadTimeout(30000);
			/* 设置post发送方式 */
			urlConn.setRequestMethod("GET");
			/* 发送commString */
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.connect();

			/* 发送完毕 获取返回流，解析流数据 */
			BufferedReader rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "GBK"));
			StringBuffer sb = new StringBuffer();
			int ch;
			while ((ch = rd.read()) > -1) {
				sb.append((char) ch);
			}
			rec_string = sb.toString().trim();
			/* 解析完毕关闭输入流 */
			rd.close();

		} catch (Exception e) {
			/* 异常处理 */
			rec_string = "-107";
			System.out.println(e);
		} finally {
			if (urlConn != null) {
				/* 关闭URL连接 */
				urlConn.disconnect();
			}
		}
		/* 返回响应内容 */
		return rec_string;
	}

	// @Override
	public ResultSMS send(List<String> mobiles, String msg) {
		String SendTime = "";
		String AppendID = "";
		String Content_Type = "8";
		String Content = msg + cnname;
		String ValidTime = "";
		String[] DesMobile = mobiles.toArray(new String[1]);
		/* post方式发送消息 */
		String postResponse = postSendMsg(username, password, Content, DesMobile, AppendID, SendTime, ValidTime,
				Content_Type);
		Response response = readResponse(postResponse);
		if ("03".equals(response.getCode())) {
			return new ResultSMS(response.getMsgid());
		} else {
			return new ResultSMS(response.getCode(), "");
		}
	}

	public Response readResponse(String xml) {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Response.class);
		return (Response) marshaller.unmarshal(new StreamSource(new ByteArrayInputStream(xml.getBytes())));
	}
}

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
class Response {
	@XmlElement(name = "code")
	String code;
	@XmlElement(name = "message")
	Message message;

	public String getMsgid() {
		return message.getMsgid();
	}

	public String getDesmobile() {
		return message.getDesmobile();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	static class Message {
		@XmlElement(name = "msgid")
		String msgid;
		@XmlElement(name = "desmobile")
		String desmobile;

		public String getMsgid() {
			return msgid;
		}

		public void setMsgid(String msgid) {
			this.msgid = msgid;
		}

		public String getDesmobile() {
			return desmobile;
		}

		public void setDesmobile(String desmobile) {
			this.desmobile = desmobile;
		}
	}
}
