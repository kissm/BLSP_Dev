
package com.lpcode.sms.client;

import org.tempuri.ArrayOfTREVSMS;

import javax.jws.WebService;

@WebService(serviceName = "sms_webservice", targetNamespace = "http://tempuri.org/", endpointInterface = "com.lpcode.sms.client.sms_webserviceSoap")
public class SmsWebserviceImpl
    implements SmsWebserviceSoap
{


    public int deleteSentsms(String sysacct, String syspwd, String staffno, String sendtime) {
        throw new UnsupportedOperationException();
    }

    public ArrayOfTREVSMS revSms(String sysacct, String syspwd) {
        throw new UnsupportedOperationException();
    }

    public int sendSms(String sysacct, String syspwd, String bscode, String staffno, String mobile, String message, String sendtime) {
        throw new UnsupportedOperationException();
    }

    public int closeRevsms(String sysacct, String syspwd, String receiveid) {
        throw new UnsupportedOperationException();
    }

}
