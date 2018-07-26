/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.exception;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vidya
 */
public class Errors
{
 public static Map<String,ASPException> getErrorList()
 {
     Map<String,ASPException> errorsMap = new HashMap<String, ASPException>();

errorsMap.put("ESP-901",new ASPException("ESP-901","Invalid Authentication Mode"));
errorsMap.put("ESP-902",new ASPException("ESP-902","Invalid ASP ID. It cannot be Empty"));
errorsMap.put("ESP-903",new ASPException("ESP-903","Invalid ASP ID. It may not exist or may be inactive."));
errorsMap.put("ESP-905",new ASPException("ESP-905","Document Hash not received"));
errorsMap.put("ESP-906",new ASPException("ESP-906","Aadhaar cannot be Empty"));
errorsMap.put("ESP-907",new ASPException("ESP-907","Request Time Stamp cannot be Empty"));
errorsMap.put("ESP-908",new ASPException("ESP-908","Request Time Stamp is not valid. Please check the server time."));
errorsMap.put("ESP-909",new ASPException("ESP-909","Transaction ID cannot be Empty"));
errorsMap.put("ESP-910",new ASPException("ESP-910","Duplicate Transaction ID for the given ASP."));
errorsMap.put("ESP-911",new ASPException("ESP-911","Input XML Signature verification failed."));
errorsMap.put("ESP-922",new ASPException("ESP-922","Invalid Signature on Input XML. Please use the corresponding certificate mapped with ESP."));
errorsMap.put("ESP-992",new ASPException("ESP-992","Input XML Parsing Error."));

return errorsMap;
 }
}
