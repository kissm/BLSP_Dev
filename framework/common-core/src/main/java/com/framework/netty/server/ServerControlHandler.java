package com.framework.netty.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;

import com.framework.netty.Server;
import com.framework.netty.spring.ApplicationContextHolder;

/**
 * 服务器运行控制器
 * 
 * @Class Name ServerControlHandler
 * @Author wangfei
 * @Create In 2014年9月28日
 */
public class ServerControlHandler {

    private static Logger log = LoggerFactory.getLogger(ServerControlHandler.class);

    private static com.framework.netty.Server server;

    private static void listenCtrl(final int cport) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    log.info("server listen to control port " + cport);
                    ServerSocket ss = new ServerSocket(cport);
                    while (true) {
                        Socket s = null;
                        s = ss.accept();
                        if (s == null)
                            continue;
                        Scanner sc = new Scanner(s.getInputStream());
                        String line = sc.nextLine();
                        if (line.equals("stop")) {
                            log.info("server is stoping...");
                            shutdown(0);
                        }
                    }
                } catch (Exception e) {
                    log.error("listen to control port fail", e);
                    shutdown(-1);
                }
            }
        }, "ctrl-thread");
        t.start();
    }

    public static void main(String[] args) throws IOException {
        if (args != null && args.length != 0 && (args[0].equals("stop"))) {
            int ctrlPort = Integer.parseInt(args[1]);
            Socket s = new Socket("localhost", ctrlPort);
            PrintWriter w = new PrintWriter(s.getOutputStream());
            w.println("stop");
            w.flush();
            w.close();
            s.close();
        } else if (args != null && args.length != 0 && args[0].equals("start")) {
            try {
                int serverPort = Integer.parseInt(args[1]!=null?args[1]:"7080");
                server = new Server(serverPort);
                listenCtrl(Integer.parseInt(args[2]));
                server.run();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                System.out.println(e.getMessage());
                shutdown(-1);
            }
        }
    }

    private static void shutdown(int code) {
        Iterator<String> iterator = server.getDispatcherServletChannelInitializer()
                .getDispatcherServlet().keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            server.getDispatcherServletChannelInitializer().getDispatcherServlet().get(key)
                    .destroy();
        }
        ApplicationContext ac = ApplicationContextHolder.getRootContext();
        ac.publishEvent(new ContextClosedEvent(ac));
        ac.publishEvent(new ContextStoppedEvent(ac));
        System.exit(code);
    }
}
