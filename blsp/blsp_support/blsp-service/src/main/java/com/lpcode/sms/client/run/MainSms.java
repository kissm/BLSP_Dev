package com.lpcode.sms.client.run;
//
//
//import com.lpcode.sms.client.SmsWebserviceClient;
//import com.lpcode.sms.client.SmsWebserviceSoap;
//import org.tempuri.SendSms;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * @author Pengs
// * @package com.lpcode.sms.client.run
// * @fileName MainSms
// * @date 16/2/23.
// */
//public class MainSms {
//    private static SendSms initSendSms() {
//        SendSms sendSms = new SendSms();
//        sendSms.setSysacct("TZSP");
//        sendSms.setSyspwd("123456");
//        sendSms.setBscode("00");
//        sendSms.setStaffno("admin");
//        sendSms.setMobile("18612112950");
//        sendSms.setMessage("珠海欢迎你123qwer@#$%^&a;sdfj;jwidjf;asdjf;alkdjsfqweihf23r");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long currentTime = System.currentTimeMillis();
//        currentTime += 60 * 1000;
//        String strDate = sdf.format(new Date(currentTime));
//        sendSms.setSendtime(strDate);
//        return sendSms;
//    }
//
//    public static void main(String[] args) {
//
//        String url = "http://192.168.2.1:8081/smsservice/sms_webservice.asmx";
//        SendSms sendSms = initSendSms();
//        String sysacct = sendSms.getSysacct();
//        String syspwd = sendSms.getSyspwd();
//        String bscode = sendSms.getBscode();
//        String mobile = sendSms.getMobile();
//        String staffno = sendSms.getStaffno();
//        String message = sendSms.getMessage();
//        String sendtime = sendSms.getSendtime();
//        if (args.length > 0) {
//            mobile = args[0];
//        }
//        if (args.length > 1) {
//            url = args[1];
//        }
//        if (args.length > 2) {
//            sysacct = args[2];
//        }
//        if (args.length > 3) {
//            syspwd = args[3];
//        }
//        if (args.length > 4) {
//            staffno = args[4];
//        }
//        if (args.length > 5) {
//            message = args[5];
//        }
//        if (args.length > 6) {
//            sendtime = args[6];
//        }
//        System.out.println("url:" + url);
//        System.out.println("sysacct:" + sysacct);
//        System.out.println("syspwd:" + syspwd);
//        System.out.println("bscode:" + bscode);
//        System.out.println("staffno:" + staffno);
//        System.out.println("mobile:" + mobile);
//        System.out.println("message:" + message);
//        System.out.println("sendtime:" + sendtime);
////        SmsWebService webservice = new SmsWebService();
////        System.out.println(webservice.SMSWEBSERVICE_WSDL_LOCATION);
//        SmsWebserviceSoap smsWebserviceSoap = new SmsWebserviceClient().getSmsWebserviceSoap(url);
//        int res = smsWebserviceSoap.sendSms(
//                sendSms.getSysacct(),
//                sendSms.getSyspwd(),
//                sendSms.getBscode(),
//                sendSms.getStaffno(),
//                sendSms.getMobile(),
//                sendSms.getMessage(),
//                sendSms.getSendtime());
//        System.out.println();
//        System.out.println("return:"+res);
//    }
//}
