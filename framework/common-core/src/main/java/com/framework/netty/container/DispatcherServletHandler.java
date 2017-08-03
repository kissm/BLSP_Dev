package com.framework.netty.container;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import com.framework.core.utils.PropertyConfigurer;
import com.framework.netty.conf.NsConfiguration;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MixedAttribute;
import io.netty.handler.stream.ChunkedStream;
import io.netty.util.CharsetUtil;

/**
 * Servlet reqeust/response转换器
 *
 * @author liufl
 * @date 2013年10月26日
 * @email hawkdowen@126.com
 */
public class DispatcherServletHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	public static final String URI_ENCODING = NsConfiguration.getInstance().getConf(NsConfiguration.KEY_URI_ENCODING);

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private final Map<String, DispatcherServlet> servlet;
	private final ServletContext servletContext;
	private Map<String, String> servletMappings;
	private boolean isDispatch;

	public DispatcherServletHandler(Map<String, DispatcherServlet> servlet, MockServletContext servletContext,
			Map<String, String> mp) {
		servletMappings = mp;
		this.servlet = servlet;
		this.servletContext = servletContext;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		boolean flag = HttpMethod.POST.equals(msg.getMethod()) || HttpMethod.GET.equals(msg.getMethod());
		if (!msg.getDecoderResult().isSuccess() || !flag) {
			// netty解析http请求失败
			// sendError(ctx, HttpResponseStatus.BAD_REQUEST,
			// "呃，NS服务器解析不了本次请求。这是一次标准的HTTP GET/POST 请求？");
			sendError(ctx, HttpResponseStatus.valueOf(500),
					PropertyConfigurer.getContextProperty("system.internal_server_error"));
			return;
		}
		// servlet请求和响应 这里使用了Spring-test包中提供的servlet api实现
		// 1.将netty的http请求转换为servlethttp请求
		MockHttpServletRequest servletRequest = createServletRequest(msg);
		// 2.创建一个空的servlethttp响应对象
		MockHttpServletResponse servletResponse = new MockHttpServletResponse();
		// 3.调用Servlet处理
		String temp = servletRequest.getRequestURI();
		for (String item : servletMappings.keySet()) {
			String tpItem = item;
			item = item.replace(".", "\\.").replace("*", ".+");
			Pattern pat = Pattern.compile(item);

			Matcher mat = pat.matcher(temp);
			if (mat.find()) {
				servlet.get(servletMappings.get(tpItem)).service(servletRequest, servletResponse);
				isDispatch = true;
				break;
			}
		}
		if (!isDispatch) {
			// 返回异常信息字符串
			sendError(ctx, HttpResponseStatus.valueOf(404), PropertyConfigurer.getContextProperty("system.not_found"));
			return;

		}

		isDispatch = false;
		// 4.将Servlethttp响应转换为Netty的Http响应并处理
		sendResponse(ctx, servletResponse);
	}

	private MockHttpServletRequest createServletRequest(FullHttpRequest msg) throws Exception {
		MockHttpServletRequest servletRequest = new MockHttpServletRequest(servletContext);
		String uri = msg.getUri();
		uri = new String(uri.getBytes("ISO8859-1"), URI_ENCODING);
		uri = URLDecoder.decode(uri, URI_ENCODING);
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(uri).build();
		// cookies
		String cookieStr = msg.headers().get("Cookie");
		if (cookieStr != null && !"".equals(cookieStr.trim())) {
			cookieStr = cookieStr.trim();
			Set<Cookie> nettyCookies = CookieDecoder.decode(msg.headers().get("Cookie"));
			List<javax.servlet.http.Cookie> servletCookies = new ArrayList<javax.servlet.http.Cookie>();
			for (Cookie nCookie : nettyCookies) {
				try {
					javax.servlet.http.Cookie sCookie = new javax.servlet.http.Cookie(nCookie.getName(),
							nCookie.getValue());
					sCookie.setComment(nCookie.getComment());
					if (nCookie.getDomain() != null) {
						sCookie.setDomain(nCookie.getDomain());
					}
					sCookie.setMaxAge((int) nCookie.getMaxAge());
					sCookie.setPath(nCookie.getPath());
					sCookie.setVersion(nCookie.getVersion());
					servletCookies.add(sCookie);
				} catch (Exception e) {
					// log.warn("无法解析的cookie", e);
				}
			}
			javax.servlet.http.Cookie[] _cookies = new javax.servlet.http.Cookie[servletCookies.size()];
			servletRequest.setCookies(servletCookies.toArray(_cookies));
		}
		// headers
		for (String name : msg.headers().names()) {
			for (String value : msg.headers().getAll(name)) {
				servletRequest.addHeader(name, value);
			}
		}
		// request method
		servletRequest.setMethod(msg.getMethod().name());
		// request uri
		String path = uriComponents.getPath();
		path = URLDecoder.decode(path, URI_ENCODING);
		servletRequest.setRequestURI(path);
		servletRequest.setPathInfo(path);
		String contexRoot = PropertyConfigurer.getContextProperty("context-root");
		if (path.startsWith(contexRoot)) {
			servletRequest.setServletPath(contexRoot);
		} else {
			throw new Exception("请输入正确的环境上下文");
		}
		// servletRequest.setCharacterEncoding("utf-8");
		if (uriComponents.getScheme() != null) {
			servletRequest.setScheme(uriComponents.getScheme());
		}
		if (uriComponents.getHost() != null) {
			servletRequest.setServerName(uriComponents.getHost());
		}
		if (uriComponents.getPort() != -1) {
			servletRequest.setServerPort(uriComponents.getPort());
		}
		// request content body
		ByteBuf content = msg.content();
		content.readerIndex(0);
		byte[] data = new byte[content.readableBytes()];
		content.readBytes(data);
		servletRequest.setContent(data);

		// request parameters
		try {
			if (uriComponents.getQuery() != null) {
				String query = UriUtils.decode(uriComponents.getQuery(), URI_ENCODING);
				servletRequest.setQueryString(query);
			}
			for (Entry<String, List<String>> entry : uriComponents.getQueryParams().entrySet()) {
				for (String value : entry.getValue()) {
					servletRequest.addParameter(UriUtils.decode(entry.getKey(), URI_ENCODING),
							UriUtils.decode(value == null ? "" : value, URI_ENCODING));
				}
			}
		} catch (UnsupportedEncodingException ex) {
			// shouldn't happen, or check your URI_ENCODING config
		}
		if (HttpMethod.POST.equals(msg.getMethod())) {
			Charset charset = Charset.forName(URI_ENCODING);
			HttpPostRequestDecoder postRequestDecoder = new HttpPostRequestDecoder(
					new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE), msg, charset);
			if (postRequestDecoder.isMultipart()) {
				List<InterfaceHttpData> dataList = postRequestDecoder.getBodyHttpDatas();
				for (InterfaceHttpData _data : dataList) {
					String name = _data.getName();
					String value = null;
					if (InterfaceHttpData.HttpDataType.Attribute == _data.getHttpDataType()) {
						// 文本域
						MixedAttribute attribute = (MixedAttribute) _data;
						attribute.setCharset(charset);
						value = attribute.getValue();
						servletRequest.addParameter(UriUtils.decode(name, URI_ENCODING),
								UriUtils.decode(value == null ? "" : value, URI_ENCODING));
					} else if (InterfaceHttpData.HttpDataType.FileUpload == _data.getHttpDataType()) {
						// 文件域，不处理，已复制request content
					} else if (InterfaceHttpData.HttpDataType.InternalAttribute == _data.getHttpDataType()) {
						// ???
						// 网上的例子都没处理，API也没有说明。。。。。。
					}
				}
			} else {
				String postContent = new String(data, Charset.forName(URI_ENCODING));
				log.debug(postContent);
				String[] params = postContent.split("&");
				for (String param : params) {
					String[] _kv = param.split("=");
					String[] kv = new String[2];
					kv[0] = _kv[0];
					if (_kv.length >= 2) {
						kv[1] = URLDecoder.decode(_kv[1], URI_ENCODING);
					} else {
						kv[1] = "";
					}
					servletRequest.addParameter(kv[0], kv[1]);
				}
			}
		}
		return servletRequest;
	}

	private void sendResponse(ChannelHandlerContext ctx, MockHttpServletResponse servletResponse) {
		HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
		if (status.equals(HttpResponseStatus.NOT_FOUND) || status.equals(HttpResponseStatus.INTERNAL_SERVER_ERROR)
				|| status.equals(HttpResponseStatus.FORBIDDEN)) {
			if (ctx.channel().isActive()) {
				sendError(ctx, status, PropertyConfigurer.getContextProperty("system.not_found"));
				return;
			}
		}
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
		for (Object name : servletResponse.getHeaderNames()) {
			for (Object value : servletResponse.getHeaders(name.toString())) {
				response.headers().add(name.toString(), value);
			}
		}
		String domain = NsConfiguration.getInstance().getConf(NsConfiguration.KEY_COOKIE_DOMAIN);
		for (javax.servlet.http.Cookie sCookie : servletResponse.getCookies()) {
			StringBuilder _cookie = new StringBuilder();
			_cookie.append(sCookie.getName()).append('=').append(sCookie.getValue());
			if (sCookie.getMaxAge() != 0) {
				long _now = System.currentTimeMillis();
				long _exp = _now + sCookie.getMaxAge() * 1000;
				Date expDate = new Date(_exp);
				String date = getGMTtime(expDate);
				_cookie.append("; expires=").append(date);
			}
			if (StringUtils.isNotEmpty(sCookie.getDomain())) {
				domain = sCookie.getDomain();
			}
			String path = "/";
			if (StringUtils.isNotEmpty(sCookie.getPath())) {
				path = sCookie.getPath();
			}
			_cookie.append("; path=").append(path);
			_cookie.append("; domain=").append(domain);
			response.headers().set(HttpHeaders.Names.SET_COOKIE, _cookie.toString());
			// XXX cookie 只有火狐认
		}
		response.headers().set(HttpHeaders.Names.CACHE_CONTROL, "private");
		response.headers().set(HttpHeaders.Names.DATE, getGMTtime(new Date()));
		response.headers().set(HttpHeaders.Names.SERVER, "NS/1.0");

		byte[] res = servletResponse.getContentAsByteArray();
		response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, res == null ? 0 : res.length);

		// Write the initial line and the header.
		ctx.write(response);
		InputStream contentStream = new ByteArrayInputStream(res);
		// Write the content.
		ChannelFuture writeFuture = ctx.writeAndFlush(new ChunkedStream(contentStream));
		writeFuture.addListener(ChannelFutureListener.CLOSE);
	}

	private String getGMTtime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z", Locale.ENGLISH);
		return dateFormat.format(date);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("ns exceptionCougnt", cause);
		if (ctx.channel().isActive()) {
			// sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR,
			// "呃，NS服务器出错了。");
			sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR,
					PropertyConfigurer.getContextProperty("system.not_found"));
		}
	}

	// private void sendErrorRedirect(ChannelHandlerContext ctx,
	// HttpResponseStatus status) {
	// String redirect = "";
	// if (status.equals(HttpResponseStatus.NOT_FOUND)) {
	// redirect =
	// NsConfiguration.getInstance().getConf(NsConfiguration.KEY_ERROR_NOT_FOUND);
	// } else if (status.equals(HttpResponseStatus.INTERNAL_SERVER_ERROR)) {
	// redirect = NsConfiguration.getInstance().getConf(
	// NsConfiguration.KEY_ERROR_INTERNAL_SERVER_ERROR);
	// } else if (status.equals(HttpResponseStatus.FORBIDDEN)) {
	// redirect =
	// NsConfiguration.getInstance().getConf(NsConfiguration.KEY_ERROR_FORBIDDEN);
	// }
	// HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
	// HttpResponseStatus.TEMPORARY_REDIRECT,
	// Unpooled.copiedBuffer("Redirect ...\r\n",
	// CharsetUtil.UTF_8));
	// response.headers().set(HttpHeaders.Names.CONTENT_TYPE,
	// "text/plain; charset=UTF-8");
	// response.headers().set(HttpHeaders.Names.LOCATION, redirect);
	// // Close the connection as soon as the error message is sent.
	// ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	// }

	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status, String msg) {
		StringBuilder error = new StringBuilder();
		error.append("{").append("status:").append(status.code()).append(",msg:").append(msg == null ? "" : msg)
				.append("}");
		HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
				Unpooled.copiedBuffer(error.toString(), CharsetUtil.UTF_8));
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
		// Close the connection as soon as the error message is sent.
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

}
