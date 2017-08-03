package com.framework.core.utils;

import java.security.Key;

import javax.crypto.Cipher;

/**
 * 说明:
 * 
 * @author kongxs
 * @date 2013-11-112 下午01:20:04
 * @modify
 */
public class DESUtils {
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数变为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前边补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 加密
     * 
     * @param arrB
     *            需要加密的字节数组
     * @param strKey
     *            加密用的key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] arrB, String strKey) throws Exception {
        Key key = getKey(strKey.getBytes());
        Cipher encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        return encryptCipher.doFinal(arrB);
    }

    /**
     * 加密
     * 
     * @param strIn
     *            需要加密的字符串
     * @param strKey
     *            加密用的key
     * @return
     * @throws Exception
     */
    public static String encrypt(String strIn, String strKey) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes(), strKey));
    }

    /**
     * 解密
     * 
     * @param arrB
     *            密文字节数组
     * @param strKey
     *            解密用的key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] arrB, String strKey) throws Exception {
        Key key = getKey(strKey.getBytes());
        Cipher decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
        return decryptCipher.doFinal(arrB);
    }

    /**
     * 解密
     * 
     * @param strIn
     *            密文字符串
     * @param strKey
     *            解密用的key
     * @return
     * @throws Exception
     */
    public static String decrypt(String strIn, String strKey) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn), strKey));
    }

    private static Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }
}
