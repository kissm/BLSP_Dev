
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
 *         <element name="SendSmsResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "sendSmsResult"
})
@XmlRootElement(name = "SendSmsResponse")
public class SendSmsResponse {

    @XmlElement(name = "SendSmsResult")
    protected int sendSmsResult;

    /**
     * Gets the value of the sendSmsResult property.
     * 
     */
    public int getSendSmsResult() {
        return sendSmsResult;
    }

    /**
     * Sets the value of the sendSmsResult property.
     * 
     */
    public void setSendSmsResult(int value) {
        this.sendSmsResult = value;
    }

}
