package com.integra.utils;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;
import org.apache.log4j.Logger;

public class SHA256Generator {

/*    public static void main(String[] args) {
        String aadhaar = "588928507592";
        String sha256aadhar = getSHA256Hash(aadhaar);
        System.out.println("sha256aadhar "+sha256aadhar);
    }*/
    private static Logger log = Logger.getLogger("ASP");
    
	public static String getSHA256Hash(String data) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(data.getBytes("UTF-8"));
            
        }catch(Exception ex) {
//            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return bytesToHex(hash); // make it printable
    }
        
        
        public static String md5Hash(String data) {
            byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            hash = digest.digest(data.getBytes("UTF-8"));
            
        }catch(Exception ex) {
//            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return bytesToHex(hash); // make it printable
    }
        
    /**
     * Use javax.xml.bind.DatatypeConverter class in JDK to convert byte array
     * to a hexadecimal string. Note that this generates hexadecimal in upper case.
     * @param hash
     * @return 
     */
    private static String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
}