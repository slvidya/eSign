/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.utils;

import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.exceptions.InvalidPdfException;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author Vidya
 */
public class PDFUtility {

    static final Logger log = Logger.getLogger(PDFUtility.class);

    public static JSONObject getHash(String SRC, String firstname,String middlename,String lastname) {
        if(middlename==null)
            middlename ="";
        if(lastname==null)
            lastname ="";
        
        byte[] content = null;
        PdfReader reader = null;
//        ByteArrayOutputStream baos = null;
        PdfStamper stamper = null;
        PdfSignatureAppearance sap = null;
        PdfSignature dic = null;
        HashMap<PdfName, Integer> exc = null;
        InputStream data = null;
        byte hash[] = null;
        Calendar cal = null;
        byte[] sh = null;
        byte[] encodedSig = null;
        byte[] paddedSig = null;
        PdfDictionary dic2 = null;
        String srcFileName;
        String hashdocument = null;
//            String TEMP = ConfigListener.aspConf.getTempfilepath() + File.separator + "_Temp.pdf";

        JSONObject responseObj = new JSONObject();
        try {

            File tempFilePath = new File(ConfigListener.aspConf.getTempfilepath());
            File[] fileArray = tempFilePath.listFiles();
            for (int i = 0; i < fileArray.length; i++) {
                boolean isDeleted = fileArray[i].delete();
                if(isDeleted)
                    log.debug("File deleted "+fileArray[i]);
            }

//            File inFile = new File(SRC);
            srcFileName = new File(SRC).getName().split("#")[1];
//            String strTmpfileName = srcFileName;
            String strTmpExt = srcFileName.substring(srcFileName.lastIndexOf(".") + 1, srcFileName.length());

            if (strTmpExt.equalsIgnoreCase("pdf") || strTmpExt.equalsIgnoreCase("jpg") || strTmpExt.equalsIgnoreCase("tiff") || strTmpExt.equalsIgnoreCase("png") || strTmpExt.equalsIgnoreCase("bmp") || strTmpExt.equalsIgnoreCase("jpeg") || strTmpExt.equalsIgnoreCase("gif")) 
            {
                if (!strTmpExt.equalsIgnoreCase("pdf")) {
                    srcFileName = srcFileName.split("\\.")[0] + ".pdf";
                    String tempPDF = ConfigListener.aspConf.getTempfilepath() + File.separator + "Temp.pdf";
                    SRC = ReadImage.createPdfFromImage(SRC, tempPDF);
                }
            } else {
                throw new InvalidPdfException("Inavlid PDF");
            }
            String DEST = ConfigListener.aspConf.getTempfilepath() + File.separator + srcFileName;

            reader = new PdfReader(SRC);
            Rectangle cropBox = reader.getCropBox(1);
            Rectangle rectangle = new Rectangle(cropBox.getRight(100), cropBox.getBottom(), cropBox.getRight(), cropBox.getBottom(90));
            FileOutputStream baos = new FileOutputStream(DEST);

//            baos = new ByteArrayOutputStream();
            stamper = PdfStamper.createSignature(reader, baos, '\0', null, true);

            sap = stamper.getSignatureAppearance();
            sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.DESCRIPTION);
            sap.setAcro6Layers(false);

            Font font = new Font();
            font.setSize(6);
            font.setFamily("Helvetica");
            font.setStyle("italic");

            sap.setLayer2Font(font);

            Calendar currentDat = Calendar.getInstance();
            currentDat.add(currentDat.MINUTE, 5);

            sap.setSignDate(currentDat);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date currentDate = currentDat.getTime();
            String strCurrentDate = dateFormat.format(currentDate);
            if (firstname != null && firstname.trim() != "") {
                sap.setLayer2Text("Signed by :" + firstname+" "+middlename+" "+lastname + "\n"
                        + "Date : " + strCurrentDate + "\n"
                        + "Location : India");
            } else {
                sap.setLayer2Text("Signed by :" + "iSign Service" + "\n"
                        + "Date : " + strCurrentDate + "\n"
                        + "Location : India");
            }

            sap.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
            sap.setImage(null);
            sap.setVisibleSignature(rectangle, 1, null);
//            sap.setVisibleSignature(rectangle, reader.getNumberOfPages(), null);

            exc = new HashMap<PdfName, Integer>();
            exc.put(PdfName.CONTENTS, new Integer(8192 * 2 + 2));

            dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
            dic.setReason(sap.getReason());
            dic.setDate(new PdfDate(sap.getSignDate()));

            sap.setCryptoDictionary(dic);
            sap.preClose(exc);

//            System.gc();

            // getting bytes of file
            InputStream is = sap.getRangeStream();
//            String hashdocument = DigestUtils.sha256Hex(Utils.readBytesFromStream(is));
            hashdocument = DigestUtils.sha256Hex(is);
            log.info("Hash Document : " + hashdocument);
            responseObj.put("downloadFileName", srcFileName);
            responseObj.put("sap", sap);
            responseObj.put("baos", baos);
            responseObj.put("hash", hashdocument);

        } catch (Exception e) {
            log.error(e);
        }
        return responseObj;
    }
}
