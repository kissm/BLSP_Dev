package com.framework.core.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DSAUtils {
    /**
     * 负责生成公钥和私钥
     * 
     * @param keysize
     *            //
     * @param path
     *            //存放路径，文件夹需以“/”结尾
     * @param name
     *            //密钥名称，程序会根据这个名称生成相应的公钥和私钥
     */
    public static boolean createKey(Integer keysize, String path, String name) {
        try {
            // 数字签名
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");

            // 根据name生成secrand
            SecureRandom secrand = new SecureRandom();
            secrand.setSeed(name.getBytes());

            // 初始化随机数产生器
            keygen.initialize(keysize, secrand);

            // 生成密钥公钥publicKey和私钥privateKey
            KeyPair keys = keygen.genKeyPair();

            // 生成密钥组，并写入文件
            PublicKey publicKey = keys.getPublic();
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(path + name + "_publicKey.dat"));
            out.writeObject(publicKey);
            out.close();

            PrivateKey privateKey = keys.getPrivate();
            out = new ObjectOutputStream(new FileOutputStream(path + name
                    + "_privateKey.dat"));
            out.writeObject(privateKey);
            out.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用私钥签名
     * 
     * @param path
     *            //文件存放路径，以文件名结尾（带后缀）
     * @param paramStr
     *            //待签名参数字符串
     * @return
     */
    public static String sign(String path, Map<String, String> paramMap) {
        try {
            // 对参数做处理
            paramMap = paraFilter(paramMap);
            String paramStr = createLinkString(paramMap);
            System.out.println("待签名字符串：" + paramStr);

            // 读取私钥
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                    path));
            PrivateKey privateKey = (PrivateKey) in.readObject();
            in.close();

            // 进行签名
            Signature signet = Signature.getInstance("DSA");
            signet.initSign(privateKey);
            signet.update(paramStr.getBytes());
            byte[] signed = signet.sign();
            String signedStr = toHexString(signed);

            return signedStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用私钥签名
     * 
     * @param byte[] byteStr 内存中的二进制对象
     * @param paramStr
     *            //待签名参数字符串
     * @return
     */
    public static String sign(byte[] byteStr, Map<String, String> paramMap) {
        try {
            // 对参数做处理
            paramMap = paraFilter(paramMap);
            String paramStr = createLinkString(paramMap);
            System.out.println("待签名字符串：" + paramStr);

            // 读取私钥
            PrivateKey privateKey = (PrivateKey) FileUtils
                    .bytesToObject(byteStr);

            // 进行签名
            Signature signet = Signature.getInstance("DSA");
            signet.initSign(privateKey);
            signet.update(paramStr.getBytes());
            byte[] signed = signet.sign();
            String signedStr = toHexString(signed);

            return signedStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用公钥校验
     * 
     * @param path
     *            //文件存放路径，以文件名结尾（带后缀）
     * @param paramStr
     *            //签名参数字符串
     * @param signedStr
     *            //签名字符串
     * @return
     */
    public static boolean verify(String path, Map<String, String> paramMap,
            String signedStr) {
        if (path == null || "".equals(path) || signedStr == null
                || "".equals(signedStr)) {
            return false;
        }
        try {
            // 对参数做处理
            paramMap = paraFilter(paramMap);
            String paramStr = createLinkString(paramMap);
            // System.out.println("待校验字符串：" + paramStr +
            // "  | signedStr = "+signedStr);

            // 读取公钥
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                    path));
            PublicKey publicKey = (PublicKey) in.readObject();
            in.close();

            // 进行校验
            Signature signetCheck = Signature.getInstance("DSA");
            signetCheck.initVerify(publicKey);
            signetCheck.update(paramStr.getBytes());
            boolean result = signetCheck.verify(toByteArray(signedStr));
            if (!result) {
                StringBuffer msg = new StringBuffer();
                msg.append("待校验字符串：" + paramStr + "  | signedStr = "
                        + signedStr + " | result" + result + " | path = "
                        + path); // +" | toByteArray(signedStr)"+new
                                 // String(toByteArray(signedStr))
                FileUtils.writeFileToDisk(toByteArray(signedStr),
                        "/var/toByteArray");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用公钥校验
     * 
     * @param byte[] byteStr //内存中的二进制对象
     * @param paramStr
     *            //签名参数字符串
     * @param signedStr
     *            //签名字符串
     * @return
     */
    public static boolean verify(byte[] byteStr, Map<String, String> paramMap,
            String signedStr) {
        if (byteStr == null || byteStr.length == 0 || signedStr == null
                || "".equals(signedStr)) {
            return false;
        }
        try {
            // 对参数做处理
            paramMap = paraFilter(paramMap);
            String paramStr = createLinkString(paramMap);
            System.out.println("待校验字符串：" + paramStr + "  | signedStr = "
                    + signedStr);

            // 读取公钥
            PublicKey publicKey = (PublicKey) FileUtils.bytesToObject(byteStr);

            // 进行校验
            Signature signetCheck = Signature.getInstance("DSA");
            signetCheck.initVerify(publicKey);
            signetCheck.update(paramStr.getBytes());
            boolean result = signetCheck.verify(toByteArray(signedStr));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static byte[] toByteArray(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            throw new IllegalArgumentException(
                    "this hexString must not be empty");
        }
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    private static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1) {
            throw new IllegalArgumentException(
                    "this byteArray must not be null or empty");
        }
        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10) {// 0~F前面不零
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }

    /**
     * 除去数组中的空值和签名参数
     * 
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || value.equals("null")
                    || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    public static void main(String args[]) {
        // DSAUtils.createKey(1024, "d:/var/sso/", "www.shopin.net");

        Map map = new HashMap();

        map.put("domainName", "www.shopin.net");
        map.put("email", "495803@163.com");
        map.put("password", "k123456");
        String signedStr = DSAUtils.sign(
                "d:/var/sso/www.shopin.net_privateKey.dat", map);
        System.out.println(signedStr);

        boolean result = DSAUtils
                .verify("d:/var/sso/www.shopin.net_publicKey.dat",
                        map,
                        "302c02141e6d89f9b3ebfc8d7a9bbee879ad9105422c018b02144699c99895e7e2079b1d4140b69ce11722c8ce8e");
        System.out.println(result);

    }
}
