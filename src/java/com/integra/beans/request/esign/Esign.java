/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.beans.request.esign;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vidya
 */
@XmlRootElement(name = "Esign")
public class Esign {
 
    private String ver;
    private String sc;
    private String ts;
    private String txn;
    private String ekycMode;
    private String ekycId;
    private String ekycIdType;
    private String aspId;
    private String AuthMode;
    private String responseSigType;
    private String preVerified;
    private String organizationFlag;
    private String responseUrl;
    
    private Docs  docs;

    public String getVer() {
        return ver;
    }

    @XmlAttribute
    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getSc() {
        return sc;
    }

    @XmlAttribute
    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getTs() {
        return ts;
    }

    @XmlAttribute
    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getTxn() {
        return txn;
    }

    @XmlAttribute
    public void setTxn(String txn) {
        this.txn = txn;
    }

    public String getEkycMode() {
        return ekycMode;
    }

    @XmlAttribute
    public void setEkycMode(String ekycMode) {
        this.ekycMode = ekycMode;
    }

    public String getEkycId() {
        return ekycId;
    }

    @XmlAttribute
    public void setEkycId(String ekycId) {
        this.ekycId = ekycId;
    }

    public String getEkycIdType() {
        return ekycIdType;
    }

    @XmlAttribute
    public void setEkycIdType(String ekycIdType) {
        this.ekycIdType = ekycIdType;
    }

    public String getAspId() {
        return aspId;
    }

    @XmlAttribute
    public void setAspId(String aspId) {
        this.aspId = aspId;
    }

    public String getAuthMode() {
        return AuthMode;
    }

    @XmlAttribute(name = "AuthMode")
    public void setAuthMode(String AuthMode) {
        this.AuthMode = AuthMode;
    }

    public String getResponseSigType() {
        return responseSigType;
    }

    @XmlAttribute
    public void setResponseSigType(String responseSigType) {
        this.responseSigType = responseSigType;
    }

    public String getPreVerified() {
        return preVerified;
    }

    @XmlAttribute
    public void setPreVerified(String preVerified) {
        this.preVerified = preVerified;
    }

    public String getOrganizationFlag() {
        return organizationFlag;
    }

    @XmlAttribute
    public void setOrganizationFlag(String organizationFlag) {
        this.organizationFlag = organizationFlag;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    @XmlAttribute
    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public Docs getDocs() {
        return docs;
    }

    @XmlElement(name="Docs")
    public void setDocs(Docs docs) {
        this.docs = docs;
    }
        
}
