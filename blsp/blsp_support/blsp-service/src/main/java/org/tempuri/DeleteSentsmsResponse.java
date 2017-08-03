
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
 *         <element name="DeleteSentsmsResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "deleteSentsmsResult"
})
@XmlRootElement(name = "DeleteSentsmsResponse")
public class DeleteSentsmsResponse {

    @XmlElement(name = "DeleteSentsmsResult")
    protected int deleteSentsmsResult;

    /**
     * Gets the value of the deleteSentsmsResult property.
     * 
     */
    public int getDeleteSentsmsResult() {
        return deleteSentsmsResult;
    }

    /**
     * Sets the value of the deleteSentsmsResult property.
     * 
     */
    public void setDeleteSentsmsResult(int value) {
        this.deleteSentsmsResult = value;
    }

}
