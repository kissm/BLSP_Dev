package com.test;

/**
 * Created by guoyuhua on 15-12-10.
 */
public class Test {
    // 在这里对输入赋值
    //public static String MValue = "ZZZ";
//    public static String Shang = null;
//    public static int M = 36;
//    public static int N = 10;
    public static void main(String[] args){
        //System.err.println(2*16*16*16*16+3*16*16*16+11*16*16+5*16+15);
        // System.err.println(3*36*36*36+4*36*36+31*36+3);
        int M = 36;
        int N = 10;

//        String nValue = "";
//        String Shang = MValue;
//        while(Shang.length() > 0) {
//            nValue = qiuyu(Shang, M, N) + nValue;
//        }
//        System.out.println(nValue);

        System.out.println(addOneToDstNumberSystem(37));

        System.out.println(changeNumberSystem("46676", 10, 36));



        System.out.println(changeNumberSystem("100", 36, 10));
        System.out.println(changeNumberSystem("ZZZ", 36, 10));
        System.out.println(changeNumberSystem("ZZZZ",36,10));
        System.out.println(changeNumberSystem("ZZZZZ",36,10));
        System.out.println(changeNumberSystem("ZZZZZZ",36,10));
        System.out.println(changeNumberSystem("ZZZZZZZ",36,10));
        System.out.println(changeNumberSystem("ZZZZZZZZ",36,10));


    }

    public static String addOneToDstNumberSystem(long decimalValue){
        long dstDecimalValue = decimalValue + 1;
        return changeNumberSystem(dstDecimalValue + "", 10, 36);
    }

    public static String changeNumberSystem(String srcValue,int srcNumberSystem, int dstNumberSystem) {
        String dstValue = "";
        String Shang ;//= srcValue;
        while(srcValue.length()>0){
            Shang = "";
            int temp = 0;
            while (srcValue.length() > 0) {
                int t = getIntFromStr(srcValue.substring(0, 1));
                srcValue = srcValue.substring(1);
                temp = temp * srcNumberSystem + t;
                Shang += getStrFromInt(temp / dstNumberSystem);
                temp = temp % dstNumberSystem;
            }
            while(Shang.length() > 0 && Shang.charAt(0) == '0'){
                Shang = Shang.substring(1);
            }
            srcValue = Shang;
            dstValue = getStrFromInt(temp) +dstValue;
        }
        return dstValue;
    }

    public static int getIntFromStr(String str){
        return str.charAt(0) <= '9' && str.charAt(0) >= '0'?
                str.charAt(0) - '0' : str.charAt(0) - 'A' + 10;
    }

    public static String getStrFromInt(int value){
        String result = null;
        if(value>=0 && value<=9)
            result = String.valueOf((char)('0' + value));
        else if(value > 9 && value <36)
        {
            result = String.valueOf((char)('A' + value - 10));
        }
        else
        {
            result = "-1";// 出错误了
        }
        return result;
    }

}
