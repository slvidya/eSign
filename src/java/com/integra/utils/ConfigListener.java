/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.utils;

import static com.integra.utils.ConfigListener.PROPERTIES_FILE_PATH;
import static com.integra.utils.ConfigListener.aspConf;
import static com.integra.utils.ConfigListener.log;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

/**
 *
 * @author vidya
 */
public class ConfigListener implements ServletContextListener {

    public static final Logger log = Logger.getLogger(ConfigListener.class);
    //public static final Map<String,String> configMap = new HashMap<String, String>();
    public static final String PROPERTIES_FILE_PATH = "ESIGN" + File.separator + "aspclient.properties";
    public static final ASPClientConfigurations aspConf = new ASPClientConfigurations();

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            log.info("initializing");
            Map<String, String> map = UtilMethods.getConfigMap(PROPERTIES_FILE_PATH);
            List<String> errors = UtilMethods.validateProperties(map);
            if (errors == null || errors.isEmpty()) {
                log.info("Constructing ASP Server");
                ASPClientConfigurations aspProp = UtilMethods.readProperties(map);
                aspConf.setAsp_webservice_uri(aspProp.getAsp_webservice_uri());
                aspConf.setEsp_webservice_uri(aspProp.getEsp_webservice_uri());
                aspConf.setTempfilepath(aspProp.getTempfilepath());
                aspConf.setUploadfilepath(aspProp.getUploadfilepath());
                aspConf.setAsp_databse_type(aspProp.getAsp_databse_type());
                aspConf.setAsp_databse_url(aspProp.getAsp_databse_url());
                aspConf.setAsp_databse_username(aspProp.getAsp_databse_username());
                aspConf.setAsp_databse_password(aspProp.getAsp_databse_password());
                ContextBean contextBean = new ContextBean();
                contextBean.setAspClientContextPath(aspConf.getAsp_webservice_uri());
                contextBean.setAspContextPath(aspConf.getAsp_webservice_uri());
                contextBean.setEspContextPath(aspConf.getEsp_webservice_uri());
                sce.getServletContext().setAttribute("contextpaths", contextBean);
                log.info("Server configured successfully");
            } else {
                log.warn("Errors found");
                for (String err : errors) {
                    log.error(err);
                }
                throw new RuntimeException("Application startup failed due to previous reasons");
            }
        } catch (Exception ex) {
            log.error("Error " + ex.getMessage());
            throw new RuntimeException("Internal Error occured");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // do nothing on context destroyed
    }
}
