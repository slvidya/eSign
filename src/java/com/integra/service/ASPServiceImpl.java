/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.service;

import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author Vidya
 */
public class ASPServiceImpl implements ASPService {

    final static Logger log = Logger.getLogger(ASPServiceImpl.class);
    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    @Override
    public String getDocHash(JSONObject jsonObj) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eSign(JSONObject jSONObject) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eSignResp(JSONObject jsonObj) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String downloadResp(JSONObject jsonObj) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET); //To change body of generated methods, choose Tools | Templates.    }
    }    @Override
    public String getAPIList(JSONObject jsonObj) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getESPList(JSONObject jsonObj) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getRequestConfig(JSONObject jsonObj) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET); //To change body of generated methods, choose Tools | Templates.
    }
}
