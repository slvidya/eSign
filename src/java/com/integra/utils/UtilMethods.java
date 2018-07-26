package com.integra.utils;

import com.integra.exception.ASPException;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import javax.xml.datatype.DatatypeConstants;
import org.apache.log4j.Logger;

/**
 *
 * @author vidya
 */
public class UtilMethods implements FilenameFilter {

    private static final Logger log = Logger.getLogger(UtilMethods.class);
    public static final String ASP_WEBCERVICE_URI = "asp.webservice.uri";
    public static final String ESP_WEBCERVICE_URI = "esp.webservice.uri";
    public static final String TEMP_FILE_PATH = "tempfilepath";
    public static final String UPLOAD_FILE_PATH = "uploadfilepath";
    public static final String ASP_DATABASE_TYPE = "asp.database.type";
    public static final String ASP_DATABASE_URL = "asp.database.url";
    public static final String ASP_DATABASE_USERNAME = "asp.database.username";
    public static final String ASP_DATABASE_PASSWORD = "asp.database.password";
    private String ext;

    /**
     * Creates a new instance of UtilMethods
     */
    public UtilMethods() {
    }

    public UtilMethods(String ext) {
        this.ext = "." + ext;
    }

    public boolean accept(File dir, String name) {
        return name.endsWith(ext);
    }

    public static String cleanStringForAjax(String strInput) {
        if (strInput == null || strInput.trim().equals("")) {
            return (" ");
        } else {
            return strInput;
        }
    }

    public static String getValueFromPropetyFile(String StrLabel) {
        Properties props = null;
        boolean isClosed = false;
        String returnvalue = null;
        try {
            props = new Properties();

            // check here
            if (File.separator.equals("\\")) {
                props.load(UtilMethods.class.getClassLoader().getResourceAsStream("ESIGN" + File.separator + "aspclient.properties"));
                returnvalue = props.getProperty(StrLabel);
            }
        } catch (Exception ex) {
            System.out.println("[UtilMethods][getValueFromPropetyFile][Exception]label Missing: " + StrLabel + " " + ex.getMessage());
            //ex.printStackTrace();
        }
        return returnvalue;

    }

    public static Map<String, String> getConfigMap(String filePath) throws ASPException {
        Map<String, String> map = null;
        InputStream in = null;
        try {
            // checks in conf folde
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            if (in == null) {
                throw new ASPException(ASPException.ERR_PROPERTIES_FILE_DOES_NOT_EXISTS, "Properties file does not exist", null);
            }
            Properties properties = new Properties();
            properties.load(in);
            map = new HashMap<String, String>();
            log.info("Properties file content");
            for (String key : properties.stringPropertyNames()) {
                log.info(key + " = " + properties.getProperty(key));
                map.put(key, properties.getProperty(key));
            }
        } catch (Exception ex) {
            log.error("Error occured", ex);
            throw new ASPException(ASPException.ERR_INTERNAL_ERROR, "Internal Error Occured while reading property file", ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    log.error("Error occured", e);
                }
            }
        }
        return map;
    }

    public static List<String> validateProperties(Map<String, String> map) throws Exception {
        List<String> errorMessage = new ArrayList<String>();
        if (map == null || map.isEmpty()) {
            errorMessage.add("Empty config map");
        } else {
            if (map.get(ASP_WEBCERVICE_URI) == null || map.get(ASP_WEBCERVICE_URI).trim().isEmpty()) {
                errorMessage.add("ASP_WEBCERVICE_URI does not found in properties file");
            }
            if (map.get(ESP_WEBCERVICE_URI) == null || map.get(ESP_WEBCERVICE_URI).trim().isEmpty()) {
                errorMessage.add("ESP_WEBCERVICE_URI does not found in properties file");
            }
            if (map.get(TEMP_FILE_PATH) == null || map.get(TEMP_FILE_PATH).trim().isEmpty()) {
                errorMessage.add("TEMP_FILE_PATH does not found in properties file");
            }
            if (map.get(UPLOAD_FILE_PATH) == null || map.get(UPLOAD_FILE_PATH).trim().isEmpty()) {
                errorMessage.add("UPLOAD_FILE_PATH does not found in properties file");
            }
            if (map.get(ASP_DATABASE_TYPE) == null || map.get(ASP_DATABASE_TYPE).trim().isEmpty()) {
                errorMessage.add("ASP_DATABASE_TYPE does not found in properties file");
            }
            if (map.get(ASP_DATABASE_URL) == null || map.get(ASP_DATABASE_URL).trim().isEmpty()) {
                errorMessage.add("ASP_DATABASE_URL does not found in properties file");
            }
            if (map.get(ASP_DATABASE_USERNAME) == null || map.get(ASP_DATABASE_USERNAME).trim().isEmpty()) {
                errorMessage.add("ASP_DATABASE_USERNAME does not found in properties file");
            }
            if (map.get(ASP_DATABASE_PASSWORD) == null || map.get(ASP_DATABASE_PASSWORD).trim().isEmpty()) {
                errorMessage.add("ASP_DATABASE_PASSWORD does not found in properties file");
            }
        }
        return errorMessage;
    }

    public static ASPClientConfigurations readProperties(Map<String, String> map) {
        ASPClientConfigurations aspConf = new ASPClientConfigurations();
        aspConf.setAsp_webservice_uri(map.get(ASP_WEBCERVICE_URI));
        aspConf.setEsp_webservice_uri(map.get(ESP_WEBCERVICE_URI));
        aspConf.setTempfilepath(map.get(TEMP_FILE_PATH));
        aspConf.setUploadfilepath(map.get(UPLOAD_FILE_PATH));
        aspConf.setAsp_databse_type(map.get(ASP_DATABASE_TYPE));
        aspConf.setAsp_databse_url(map.get(ASP_DATABASE_URL));
        aspConf.setAsp_databse_username(map.get(ASP_DATABASE_USERNAME));
        aspConf.setAsp_databse_password(map.get(ASP_DATABASE_PASSWORD));
        return aspConf;
    }

    public static String getTimeinIST() throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String timeStamp = XMLGregorianCalendarImpl.createDateTime(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                calendar.get(Calendar.MILLISECOND), DatatypeConstants.FIELD_UNDEFINED).toString();

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//
//        TimeZone timeZone=TimeZone.getTimeZone("IST");
//        sdf.setTimeZone(timeZone);
//        String formattedDate=sdf.format(new Date());
//        return formattedDate;
        return timeStamp;
    }

    public static String getTXNId() {
//        return Long.toHexString(Double.doubleToLongBits(Math.random()));
        return UUID.randomUUID().toString();
    }
}
