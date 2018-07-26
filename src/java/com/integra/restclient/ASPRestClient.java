/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.restclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.log4j.Logger;

/**
 *
 * @author vidya
 */
public class ASPRestClient {

    private static Logger log = Logger.getLogger("ASP");
    private WebResource webResource;
    private Client client;

    /*    public ASPClient(String baseURI, boolean isLoggingAllowed) {
     ClientConfig config = new DefaultClientConfig();
     client = Client.create(config);
     client.setConnectTimeout(30000);


     if (isLoggingAllowed) {
     client.addFilter(new LoggingFilter());
     }
     webResource = client.resource(baseURI);

     if (baseURI.contains("https")) {
     //            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
     System.setProperty("https.protocols", "TLSv1");
     }
     }*/
    public ASPRestClient(String baseURI, boolean isLoggingAllowed) {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // do nothing and blindly accept the certificate
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // do nothing and blindly accept the server
                    }
                }};
            SSLContext context = SSLContext.getInstance("TLS");
            
            context.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
            ClientConfig config = new DefaultClientConfig();
            config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            }, context));
            client = Client.create(config);
            if (isLoggingAllowed) {
                client.addFilter(new LoggingFilter());
            }
            client.setFollowRedirects(true);
            webResource = client.resource(baseURI);

        } catch (NoSuchAlgorithmException ex) {
            log.error(ex.getMessage());
        } catch (KeyManagementException ex) {
            log.error(ex.getMessage());
        }
    }

    public String downloadResponse() {
        ClientResponse response = null;
        try {
            response = webResource.path("/download").type("text/plain").get(ClientResponse.class);
            this.checkResponseCode(response);
        } catch (Exception e) {
            log.error(e.getMessage());
//            e.printStackTrace();
        }
        if (response != null) {
            return response.getEntity(String.class);
        } else {
            return "Error";
        }

    }

    public String eSign() {
        ClientResponse response = null;
        try {
            response = webResource.path("/esignauth").type("text/plain").post(ClientResponse.class);
            this.checkResponseCode(response);
        } catch (Exception e) {
            log.error(e.getMessage());
//            e.printStackTrace();
        }
        if (response != null) {
            return response.getEntity(String.class);
        } else {
            return "Error";
        }

    }

    private void checkResponseCode(ClientResponse response) throws Exception {
        switch (response.getStatus()) {
            case 400:
                throw new Exception(response.getEntity(String.class));
            case 401:
                throw new Exception("401:Unauthorized Access");
            case 403:
                throw new Exception("403:Forbidden");
            case 404:
                throw new Exception("404:Invalid URI path");
            case 405:
                throw new Exception("405:Method not allowed");
            case 408:
                throw new Exception("408:Request Timeout");
            case 500:
                throw new Exception("500:Internal server error");
            case 503:
                throw new Exception("503:Service unavailable");
            default:
                throw new Exception("Exception Occured");
        }
    }
}
