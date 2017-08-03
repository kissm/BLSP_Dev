
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfTREVSMS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="ArrayOfTREVSMS">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="TREVSMS" type="{http://tempuri.org/}TREVSMS" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTREVSMS", propOrder = {
    "trevsms"
})
public class ArrayOfTREVSMS {

    @XmlElement(name = "TREVSMS", required = true)
    protected List<TREVSMS> trevsms;

    /**
     * Gets the value of the trevsms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trevsms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTREVSMS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TREVSMS }
     * 
     * 
     */
    public List<TREVSMS> getTREVSMS() {
        if (trevsms == null) {
            trevsms = new ArrayList<TREVSMS>();
        }
        return this.trevsms;
    }

}
