
package com.lpcode.sms.client;

import org.tempuri.ArrayOfTREVSMS;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "sms_webserviceSoap", targetNamespace = "http://tempuri.org/")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface SmsWebserviceSoap {


    @WebMethod(operationName = "DeleteSentsms", action = "http://tempuri.org/DeleteSentsms")
    @WebResult(name = "DeleteSentsmsResult", targetNamespace = "http://tempuri.org/")
    public int deleteSentsms(
            @WebParam(name = "sysacct", targetNamespace = "http://tempuri.org/")
            String sysacct,
            @WebParam(name = "syspwd", targetNamespace = "http://tempuri.org/")
            String syspwd,
            @WebParam(name = "staffno", targetNamespace = "http://tempuri.org/")
            String staffno,
            @WebParam(name = "sendtime", targetNamespace = "http://tempuri.org/")
            String sendtime);

    @WebMethod(operationName = "RevSms", action = "http://tempuri.org/RevSms")
    @WebResult(name = "RevSmsResult", targetNamespace = "http://tempuri.org/")
    public ArrayOfTREVSMS revSms(
            @WebParam(name = "sysacct", targetNamespace = "http://tempuri.org/")
            String sysacct,
            @WebParam(name = "syspwd", targetNamespace = "http://tempuri.org/")
            String syspwd);

    @WebMethod(operationName = "SendSms", action = "http://tempuri.org/SendSms")
    @WebResult(name = "SendSmsResult", targetNamespace = "http://tempuri.org/")
    public int sendSms(
            @WebParam(name = "sysacct", targetNamespace = "http://tempuri.org/")
            String sysacct,
            @WebParam(name = "syspwd", targetNamespace = "http://tempuri.org/")
            String syspwd,
            @WebParam(name = "bscode", targetNamespace = "http://tempuri.org/")
            String bscode,
            @WebParam(name = "staffno", targetNamespace = "http://tempuri.org/")
            String staffno,
            @WebParam(name = "mobile", targetNamespace = "http://tempuri.org/")
            String mobile,
            @WebParam(name = "message", targetNamespace = "http://tempuri.org/")
            String message,
            @WebParam(name = "sendtime", targetNamespace = "http://tempuri.org/")
            String sendtime);

    @WebMethod(operationName = "CloseRevsms", action = "http://tempuri.org/CloseRevsms")
    @WebResult(name = "CloseRevsmsResult", targetNamespace = "http://tempuri.org/")
    public int closeRevsms(
            @WebParam(name = "sysacct", targetNamespace = "http://tempuri.org/")
            String sysacct,
            @WebParam(name = "syspwd", targetNamespace = "http://tempuri.org/")
            String syspwd,
            @WebParam(name = "receiveid", targetNamespace = "http://tempuri.org/")
            String receiveid);

}
