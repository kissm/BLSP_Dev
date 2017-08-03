
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
 *         <element name="CloseRevsmsResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "closeRevsmsResult"
})
@XmlRootElement(name = "CloseRevsmsResponse")
public class CloseRevsmsResponse {

    @XmlElement(name = "CloseRevsmsResult")
    protected int closeRevsmsResult;

    /**
     * Gets the value of the closeRevsmsResult property.
     * 
     */
    public int getCloseRevsmsResult() {
        return closeRevsmsResult;
    }

    /**
     * Sets the value of the closeRevsmsResult property.
     * 
     */
    public void setCloseRevsmsResult(int value) {
        this.closeRevsmsResult = value;
    }

}
