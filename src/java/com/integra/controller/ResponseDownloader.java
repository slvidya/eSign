/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.controller;

import com.integra.restclient.ASPRestClient;
import com.integra.utils.ConfigListener;

/**
 *
 * @author Vidya
 */
public class ResponseDownloader {

    public static void main(String[] args) {
        ResponseDownloader downloader = new ResponseDownloader();
        downloader.getResponse();
    }
    public String getResponse(){
//        String BASE_URI = ConfigListener.aspConf.getAsp_webservice_uri();

        ASPRestClient aSPClient = new ASPRestClient("https://182.72.231.117:9945/iASP/rest/services", true);
        String pkcs7Response = aSPClient.downloadResponse();
        return pkcs7Response;
    }
}
