/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.controller;

import com.integra.aspclient.ASPClient;
import com.integra.utils.JSONValidator;
import org.apache.log4j.Logger;

/**
 *
 * @author Vidya
 */
public class AuthController{

    private static Logger log = Logger.getLogger(AuthController.class);

    public String authenticateDemo(String demoDetails) {

        log.info("[AuthController][authenticateDemo][demoDetails] " + demoDetails);

        String BASE_URI = null;
        String returnData = "";
        try {
//            BASE_URI = ConfigListener.aspConf.getEsp_webservice_uri();
            BASE_URI = "http://10.10.10.86:8080/iASKWeb/rest/rasf/asa/";
            log.info("[AuthController][authenticateDemo][BASE_URI] " + BASE_URI);

            ASPClient client = new ASPClient(BASE_URI, true);
            String resp = client.demoAuth(demoDetails);

            if (resp != null) {
                if (JSONValidator.isJSONValid(resp)) {
                    returnData = resp;
                } else {
                    returnData = "Invalid Response From AUA";
                }
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return returnData;
    }
}

