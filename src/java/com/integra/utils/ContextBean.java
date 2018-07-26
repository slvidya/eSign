/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.utils;

/**
 *
 * @author Vidya
 */
public class ContextBean {
private String aspClientContextPath;    
private String aspContextPath;    
private String espContextPath;    

    public String getAspClientContextPath() {
        return aspClientContextPath;
    }

    public void setAspClientContextPath(String aspClientContextPath) {
        this.aspClientContextPath = aspClientContextPath;
    }

    public String getAspContextPath() {
        return aspContextPath;
    }

    public void setAspContextPath(String aspContextPath) {
        this.aspContextPath = aspContextPath;
    }

    public String getEspContextPath() {
        return espContextPath;
    }

    public void setEspContextPath(String espContextPath) {
        this.espContextPath = espContextPath;
    }

}
