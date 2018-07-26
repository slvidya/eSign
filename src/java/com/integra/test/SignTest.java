/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.test;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 *
 * @author Vidya
 */
public class SignTest {
public void foo(String s) {
	 System.out.println("String");
	 }

	 public void foo(StringBuffer sb){
	 System.out.println("StringBuffer");
	 }

	 public static void main(String[] args) {
		new SignTest().foo("");
	}
//    public static void main(String[] args) {
//        try {
//
//            
//            PdfReader reader = new PdfReader("D:\\VIDYA\\working projects\\2016\\september\\documents\\aadhaar_authentication_api_1_6.pdf");
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            PdfStamper stamper = PdfStamper.createSignature(reader, baos, '\0');
//            PdfSignatureAppearance sap = stamper.getSignatureAppearance();
//            sap.setReason("Test");
//            sap.setVisibleSignature(new Rectangle(10, 10, 150, 80), 1, "");
//            sap.setLayer2Text("layer2text");
//
//            PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
//            dic.setReason(sap.getReason());
//            dic.setDate(new PdfDate(sap.getSignDate()));
//            sap.setCryptoDictionary(dic);
//            HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
//            exc.put(PdfName.CONTENTS, new Integer(8192 * 2));
//            sap.preClose(exc);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
