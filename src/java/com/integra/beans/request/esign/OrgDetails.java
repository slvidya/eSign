/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.beans.request.esign;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Vidya
 */
@XmlRootElement(name="OrgDetails")
public class OrgDetails {
    
    private String base64OrgXML;

    public String getBase64OrgXML() {
        return base64OrgXML;
    }

    @XmlValue
    public void setBase64OrgXML(String base64OrgXML) {
        this.base64OrgXML = base64OrgXML;
    }
    
    
}
