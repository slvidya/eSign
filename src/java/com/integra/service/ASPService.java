/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 *
 * @author Vidya
 */
public interface ASPService {
String getDocHash(JSONObject jsonObj);
String eSign(JSONObject jsonObj);
String eSignResp(JSONObject jsonObj);    
String downloadResp(JSONObject jsonObj);
String getAPIList(JSONObject jsonObj);
String getESPList(JSONObject jsonObj);
String getRequestConfig(JSONObject jsonObj);
}
