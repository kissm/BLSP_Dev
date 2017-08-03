package com.framework.netty.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * NS 配置
 * 
 * @author liufl
 * @date 20140919
 * @email hawkdowen@126.com
 */
public class NsConfiguration {

    private static NsConfiguration instance = new NsConfiguration();;

    private static void init(NsConfiguration instance, String conf)
            throws IOException {
        if (conf != null && conf.length() != 0) {
            FileInputStream in = new FileInputStream(conf);
            instance.properties.load(in);
            
        }
    }

    public static void init(String conf) throws IOException {
        if (conf != null && conf.length() != 0) {
            FileInputStream in = new FileInputStream(conf);
            instance.properties.load(in);
        }
    }

    public static NsConfiguration getInstance(String conf) throws IOException {
        init(instance, conf);
        return instance;
    }

    public static NsConfiguration getInstance() {
        try {
            return getInstance(null);
        } catch (IOException e) {
            // will not happen
            return null;
        }
    }

    public static final String KEY_R_DIR = "resourceDir";
    public static final String KEY_URI_ENCODING = "uriEncoding";
    public static final String KEY_SERVER_CONTROL_PORT = "cport";
    public static final String KEY_COOKIE_DOMAIN = "cookieDomain";

    private Properties properties = new Properties();

    protected NsConfiguration() {
        this.properties.put(KEY_R_DIR, "resources");
        this.properties.put(KEY_URI_ENCODING, "UTF-8");
        this.properties.put(KEY_SERVER_CONTROL_PORT, "8085");
        this.properties.put(KEY_COOKIE_DOMAIN, "localhost");
    };

    public String getConf(String key) {
        return this.properties.getProperty(key);
    }

}
