/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.beans.request.esign;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vidya
 */
@XmlRootElement(name = "Docs")
public class Docs {
    private InputHash inputHash;

    public InputHash getInputHash() {
        return inputHash;
    }

    @XmlElement(name = "InputHash")
    public void setInputHash(InputHash inputHash) {
        this.inputHash = inputHash;
    }
}
