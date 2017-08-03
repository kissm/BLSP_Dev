
package com.lpcode.sms.client;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

public class SmsWebserviceClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public SmsWebserviceClient() {
        create0();
        Endpoint SmsWebserviceSoapLocalEndpointEP = service0 .addEndpoint(new QName("http://tempuri.org/", "SmsWebserviceSoapLocalEndpoint"), new QName("http://tempuri.org/", "SmsWebserviceSoapLocalBinding"), "xfire.local://sms_webservice");
        endpoints.put(new QName("http://tempuri.org/", "SmsWebserviceSoapLocalEndpoint"), SmsWebserviceSoapLocalEndpointEP);
        Endpoint SmsWebserviceSoapEP = service0 .addEndpoint(new QName("http://tempuri.org/", "SmsWebserviceSoap"), new QName("http://tempuri.org/", "SmsWebserviceSoap"), "http://192.168.2.1:8081/smsservice/sms_webservice.asmx");
        endpoints.put(new QName("http://tempuri.org/", "SmsWebserviceSoap"), SmsWebserviceSoapEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    public Collection getEndpoints() {
        return endpoints.values();
    }

    private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((com.lpcode.sms.client.SmsWebserviceSoap.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://tempuri.org/", "SmsWebserviceSoapLocalBinding"), "urn:xfire:transport:local");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://tempuri.org/", "SmsWebserviceSoap"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public SmsWebserviceSoap getSmsWebserviceSoapLocalEndpoint() {
        return ((SmsWebserviceSoap)(this).getEndpoint(new QName("http://tempuri.org/", "SmsWebserviceSoapLocalEndpoint")));
    }

    public SmsWebserviceSoap getSmsWebserviceSoapLocalEndpoint(String url) {
        SmsWebserviceSoap var = getSmsWebserviceSoapLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public SmsWebserviceSoap getSmsWebserviceSoap() {
        return ((SmsWebserviceSoap)(this).getEndpoint(new QName("http://tempuri.org/", "SmsWebserviceSoap")));
    }

    public SmsWebserviceSoap getSmsWebserviceSoap(String url) {
        SmsWebserviceSoap var = getSmsWebserviceSoap();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

}
