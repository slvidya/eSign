/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.beans.request.esign;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Vidya
 */
@XmlRootElement(name = "InputHash")
public class InputHash {
    
    private String id;
    private String hashAlgorithm;
    private String docInfo;
    private String docHash;

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    @XmlAttribute
    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public String getDocInfo() {
        return docInfo;
    }

    @XmlAttribute
    public void setDocInfo(String docInfo) {
        this.docInfo = docInfo;
    }

    public String getDocHash() {
        return docHash;
    }

    @XmlValue
    public void setDocHash(String docHash) {
        this.docHash = docHash;
    }    
}
