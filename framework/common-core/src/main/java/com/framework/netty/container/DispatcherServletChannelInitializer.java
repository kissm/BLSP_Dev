package com.framework.netty.container;

import com.framework.core.utils.PropertyConfigurer;
import com.framework.netty.spring.ApplicationContextHolder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mvc servlet启动器
 * 
 * @author liufl
 * @date 2013年10月26日
 * @email hawkdowen@126.com
 */
public class DispatcherServletChannelInitializer extends ChannelInitializer<SocketChannel> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final Map<String, DispatcherServlet> dispatcherServlet;

    private final MockServletContext servletContext;

    private Map<String, String> servletMappings;

    public DispatcherServletChannelInitializer() throws Exception {
        log.info("init spring and spring mvc");

        servletContext = new MockServletContext();
        dispatcherServlet = new ConcurrentHashMap<String, DispatcherServlet>();

        String[] slets = PropertyConfigurer.getContextProperty("system.servlets").split(",");
        for (String item : slets) {
            MockServletConfig servletConfig = new MockServletConfig(servletContext, item);
            DispatcherServlet rItem = new DispatcherServlet(ApplicationContextHolder.getMvcContext());
            rItem.init(servletConfig);
            dispatcherServlet.put(item, rItem);
        }

        servletMappings = new ConcurrentHashMap<String, String>();
        String[] strs = PropertyConfigurer.getContextProperty("system.suffix").split(",");
        for (String str : strs) {
            String[] sletsuf = str.split(":");
            servletMappings.put(sletsuf[1], sletsuf[0]);
        }

        log.info("spring and spring mvc inited");
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(20971520)); // 上传限制3M
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
        pipeline.addLast("handler", new DispatcherServletHandler(
                this.dispatcherServlet, this.servletContext,
                this.servletMappings));
    }

    public Map<String, DispatcherServlet> getDispatcherServlet() {
        return dispatcherServlet;
    }

}
