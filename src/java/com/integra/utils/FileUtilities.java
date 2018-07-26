/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author Vidya
 */
public class FileUtilities {

    private static Logger log = Logger.getLogger("ASP");

    public static String readFileToString(String filePath) throws Exception {

        byte[] publicKeyCert = null;
//        System.out.println("[getPublicCert]getting certificate");
        log.info("[getPublicCert]getting certificate");

        File f = new File(filePath);
        StringBuilder strFileContents = new StringBuilder();
        InputStream ism = null;
        BufferedInputStream in = null;
        try {
            ism = new FileInputStream(f);
            in = new java.io.BufferedInputStream(ism);
            publicKeyCert = new byte[4096];
            int bytesRead = 0;
            while ((bytesRead = in.read(publicKeyCert)) != -1) {
                strFileContents = strFileContents.append(new String(publicKeyCert, 0, bytesRead));
            }
        } catch (Exception e1) {
            log.error(e1.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
            if (ism != null) {
                ism.close();
            }
        }
        return strFileContents.toString();
    }
}
