package com.framework.fdfs.test;

import com.framework.fdfs.images.FastDFSFileFactory;
import com.framework.fdfs.images.JpgFile;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wangjunwei on 15-5-16.
 */
public class Test {
    public static void main(String[] args){
        long time = System.currentTimeMillis();
        for (int i = 0;i<1;i++) {
            try {
                InputStream in ;//= new FileInputStream(new File("/Users/guoyuhua/Downloads/d04c8df440cef477ed5097001152c892.jpg"));
                URL url = new URL("http://img.17hcz.com/public/images/2a/94/17/d04c8df440cef477ed5097001152c892.jpg");
                URLConnection rulConnection = url.openConnection();
                HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
                httpUrlConnection.setDoInput(true);
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setReadTimeout(1000*20);
                httpUrlConnection.setConnectTimeout(1000 * 20);
                in = httpUrlConnection.getInputStream();
                JpgFile j = new JpgFile();

                FastDFSFileFactory.getInstance().saveFile(in, j);
                System.out.println(j.getUrl() + "      \r\n" + j.getFileid());

//                HessianProxyFactory factory = new HessianProxyFactory();
//                try {
//                    CutService<PngFile> service = (CutService<PngFile>) factory.create(CutService.class,"http://10.10.1.153:8081/images/service/cutImageService");
//                    PngFile nj = service.cut(j,100,100);
//                    System.out.println(nj.getUrl()+"     \n"+nj.getFileid());
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis()-time);
    }
}
