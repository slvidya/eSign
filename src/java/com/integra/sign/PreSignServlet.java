/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.sign;

import com.integra.utils.FileUtilities;
import com.integra.utils.SHA256Generator;
import com.integra.utils.UtilMethods;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Vidya
 */
public class PreSignServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/octet-stream");
        try {

            // we create a reader and a stamper
/*            PdfReader reader = new PdfReader("D:\\VIDYA\\working projects\\2016\\september\\documents\\aadhaar_authentication_api_1_6.pdf");
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfStamper stamper = PdfStamper.createSignature(reader, baos, '\0');

             // we create the signature appearance
             PdfSignatureAppearance sap = stamper.getSignatureAppearance();
             sap.setReason("Test");
             sap.setLocation("On a server!");
             sap.setVisibleSignature(new Rectangle(36, 748, 144, 780), 1, "sig");
             //            sap.setCertificationLevel(0);

             // we create the signature infrastructure
             PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
             dic.setReason(sap.getReason());
             dic.setLocation(sap.getLocation());
             dic.setContact(sap.getContact());
             dic.setDate(new PdfDate(sap.getSignDate()));
             sap.setCryptoDictionary(dic);

             HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
             exc.put(PdfName.CONTENTS, new Integer(8192 * 2 + 2));
             sap.preClose(exc);
             */
            PdfReader reader = new PdfReader("D:\\VIDYA\\working projects\\2016\\september\\documents\\aadhaar_authentication_api_1_6.pdf");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfStamper stamper = PdfStamper.createSignature(reader, baos, '\0');

            PdfSignatureAppearance sap = stamper.getSignatureAppearance();
            sap.setReason("Test");
            sap.setVisibleSignature(new Rectangle(10, 10, 150, 80), 1, "");
            
            PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
            dic.setReason(sap.getReason());
            dic.setDate(new PdfDate(sap.getSignDate()));
            sap.setCryptoDictionary(dic);
            
            HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
            exc.put(PdfName.CONTENTS, new Integer(8192 * 2+2));
            sap.preClose(exc);
            
            InputStream data = sap.getRangeStream();
            String hash = DigestUtils.sha256Hex(data);
            
            System.out.println("hash " + hash);
            byte[] sh = hash.getBytes();

            // We store the objects we'll need for post signing in a session
            HttpSession session = req.getSession(true);
            session.setAttribute("sap", sap);
            session.setAttribute("baos", baos);

            // we write the hash that needs to be signed to the HttpResponse output
            OutputStream os = resp.getOutputStream();
            os.write(sh, 0, sh.length);
            os.flush();
            os.close();
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }
}
