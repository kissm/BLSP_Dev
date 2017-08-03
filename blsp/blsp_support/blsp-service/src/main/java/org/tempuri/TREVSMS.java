
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TREVSMS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="TREVSMS">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="smsid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="sendmobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="revmobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="smsmemo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="revtime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TREVSMS", propOrder = {
    "smsid",
    "sendmobile",
    "revmobile",
    "smsmemo",
    "revtime"
})
public class TREVSMS {

    protected String smsid;
    protected String sendmobile;
    protected String revmobile;
    protected String smsmemo;
    protected String revtime;

    /**
     * Gets the value of the smsid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsid() {
        return smsid;
    }

    /**
     * Sets the value of the smsid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsid(String value) {
        this.smsid = value;
    }

    /**
     * Gets the value of the sendmobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendmobile() {
        return sendmobile;
    }

    /**
     * Sets the value of the sendmobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendmobile(String value) {
        this.sendmobile = value;
    }

    /**
     * Gets the value of the revmobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevmobile() {
        return revmobile;
    }

    /**
     * Sets the value of the revmobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevmobile(String value) {
        this.revmobile = value;
    }

    /**
     * Gets the value of the smsmemo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsmemo() {
        return smsmemo;
    }

    /**
     * Sets the value of the smsmemo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsmemo(String value) {
        this.smsmemo = value;
    }

    /**
     * Gets the value of the revtime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevtime() {
        return revtime;
    }

    /**
     * Sets the value of the revtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevtime(String value) {
        this.revtime = value;
    }

}
