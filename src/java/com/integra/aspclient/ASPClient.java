/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.aspclient;

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
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;

/**
 *
 * @author vidya
 */
public class ASPClient {

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
    public ASPClient(String baseURI, boolean isLoggingAllowed) {
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
            log.info("[ASPClient][downloadResponse]");
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

    public String demoAuth(String demoAuthDetails) {
        ClientResponse response = null;
        try {
            log.info("[ASPClient][demoAuth]");
            response = webResource.path("/authenticate").accept("application/json").type("application/json").post(ClientResponse.class, demoAuthDetails);

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

    public String getOTP(String JSONobj) {
        ClientResponse response = null;
        try {
            log.info("[UIDClient][getkycnpcidata] [request json]" + JSONobj);
            response = webResource.path("/getotp").type("application/json").post(ClientResponse.class, JSONobj);
            this.checkResponseCode(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (response != null) {
            return response.getEntity(String.class);
        } else {
            return null;
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
            case 200:
                System.out.println("success");
            default:
                throw new Exception("Exception Occured");
        }
    }
}
