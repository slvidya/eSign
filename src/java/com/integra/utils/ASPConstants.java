/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.utils;

/**
 *
 * @author vidya
 */
public interface ASPConstants
{
/*Auth attributes
 * AUTH_TID : terminal ID
 * AUTH_VER : API version
 */
String AUTH_TID="public";
String AUTH_VER="1.6";
    
String PID_VER="1.0";

/*
 * Auth element 'meta' : METADATA of the device and transaction
 * METADATA_UDC : unique device code
 * METADATA_FDC : fingerprint device code
 * METADATA_IDC : iris device code
 * METADATA_PIP : public IP address of the device
 * METADATA_LOT : location type(G - Gio location or P - Pincode)
 * METADATA_LOV : location value
*/
//public static String METADATA_UDC="UIDAI:SampleClient";
String METADATA_UDC="IMS18Oct2017574939";
String METADATA_FDC="NA";
String METADATA_IDC="NA";
String METADATA_PIP="NA"; // can be NA or "127.0.0.1"
String METADATA_LOT="P";
String METADATA_LOV="560064";

/*
 * Auth element 'data' : Contains the encrypted “Pid” element in base-64 format.
 * DATA_TYPE : type of PID value(X - XML or P - Protobuf)
 */
String DATA_TYPE="X";

String INPUT_ID="1";
String INPUT_HASH_ALGORITHM="SHA256";

String ESIGN_ver="2.0";
//public static String ESIGN_sc="Y";  
String ESIGN_EKYCMODE="U";
String ESIGN_EKYCIDTYPE="A";
String ESIGN_AUTHMODE="1";
String ESIGN_RESP_SIGN_TYPE="pkcs7";
String ESIGN_ORG_FLAG="N";
}
