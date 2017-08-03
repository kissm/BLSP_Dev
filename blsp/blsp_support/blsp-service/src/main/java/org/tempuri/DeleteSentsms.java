
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="sysacct" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="syspwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="staffno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="sendtime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sysacct",
    "syspwd",
    "staffno",
    "sendtime"
})
@XmlRootElement(name = "DeleteSentsms")
public class DeleteSentsms {

    protected String sysacct;
    protected String syspwd;
    protected String staffno;
    protected String sendtime;

    /**
     * Gets the value of the sysacct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysacct() {
        return sysacct;
    }

    /**
     * Sets the value of the sysacct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysacct(String value) {
        this.sysacct = value;
    }

    /**
     * Gets the value of the syspwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSyspwd() {
        return syspwd;
    }

    /**
     * Sets the value of the syspwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSyspwd(String value) {
        this.syspwd = value;
    }

    /**
     * Gets the value of the staffno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaffno() {
        return staffno;
    }

    /**
     * Sets the value of the staffno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaffno(String value) {
        this.staffno = value;
    }

    /**
     * Gets the value of the sendtime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendtime() {
        return sendtime;
    }

    /**
     * Sets the value of the sendtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendtime(String value) {
        this.sendtime = value;
    }

}
