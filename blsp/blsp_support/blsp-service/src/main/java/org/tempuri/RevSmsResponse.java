
package org.tempuri;

import javax.xml.bind.annotation.*;


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
 *         <element name="RevSmsResult" type="{http://tempuri.org/}ArrayOfTREVSMS" minOccurs="0"/>
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
    "revSmsResult"
})
@XmlRootElement(name = "RevSmsResponse")
public class RevSmsResponse {

    @XmlElement(name = "RevSmsResult")
    protected ArrayOfTREVSMS revSmsResult;

    /**
     * Gets the value of the revSmsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTREVSMS }
     *     
     */
    public ArrayOfTREVSMS getRevSmsResult() {
        return revSmsResult;
    }

    /**
     * Sets the value of the revSmsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTREVSMS }
     *     
     */
    public void setRevSmsResult(ArrayOfTREVSMS value) {
        this.revSmsResult = value;
    }

}
