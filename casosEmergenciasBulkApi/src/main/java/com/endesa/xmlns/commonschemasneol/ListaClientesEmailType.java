
package com.endesa.xmlns.commonschemasneol;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaClientesEmailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaClientesEmailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ClienteEmailAltaMod" type="{http://xmlns.endesa.com/CommonSchemasNeol}ClienteEmailAltaModType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaClientesEmailType", propOrder = {
    "clienteEmailAltaMod"
})
public class ListaClientesEmailType {

    @XmlElement(name = "ClienteEmailAltaMod", required = true)
    protected List<ClienteEmailAltaModType> clienteEmailAltaMod;

    /**
     * Gets the value of the clienteEmailAltaMod property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clienteEmailAltaMod property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClienteEmailAltaMod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClienteEmailAltaModType }
     * 
     * 
     */
    public List<ClienteEmailAltaModType> getClienteEmailAltaMod() {
        if (clienteEmailAltaMod == null) {
            clienteEmailAltaMod = new ArrayList<ClienteEmailAltaModType>();
        }
        return this.clienteEmailAltaMod;
    }

}
