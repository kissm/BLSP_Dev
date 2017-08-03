package com.framework.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.netty.container.DispatcherServletChannelInitializer;
import com.framework.netty.spring.ApplicationContextHolder;

/**
 * server
 *
 * @author liufl
 * @date 20130916
 * @email hawkdowen@126.com
 */
public class Server {

  private Logger log = LoggerFactory.getLogger(this.getClass());


  private final int                                 port;
  private       DispatcherServletChannelInitializer childHandler;
  private       ServerBootstrap                     bootstrap;

  public Server(int port) {
    this.port = port;
  }

  public void run() throws Exception {
    if (this.bootstrap != null) {
      throw new IllegalStateException("服务器已启动，请不要重复主动");
    }
    bootstrap = new ServerBootstrap();
    bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup()).channel(NioServerSocketChannel.class);
    ApplicationContextHolder.init();
    childHandler = new DispatcherServletChannelInitializer();
    bootstrap.childHandler(childHandler);
    log.info("server listens to port " + port);
    ChannelFuture future = bootstrap.localAddress(port).bind().sync();
    future.addListener(new GenericFutureListener<Future<? super Void>>() {
      public void operationComplete(Future<? super Void> future)
      throws Exception {
        log.info("server startup");
      }
    });
    future.channel().closeFuture().sync();
  }

  public DispatcherServletChannelInitializer getDispatcherServletChannelInitializer() {
    return childHandler;
  }

  public ServerBootstrap getBootstrap() {
    return bootstrap;
  }
}
