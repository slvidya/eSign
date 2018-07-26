/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.sign;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.codec.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vidya
 */
public class PostSignServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        resp.setContentType("application/octet-stream");

        // we get the objects we need for postsigning from the session
        HttpSession session = req.getSession(false);
//        PdfPKCS7 sgn = (PdfPKCS7) session.getAttribute("sgn");
//        byte[] hash = (byte[]) session.getAttribute("hash");
//        Calendar cal = (Calendar) session.getAttribute("cal");
        PdfSignatureAppearance sap = (PdfSignatureAppearance) session.getAttribute("sap");
        ByteArrayOutputStream os = (ByteArrayOutputStream) session.getAttribute("baos");
        session.invalidate();

        // we read the signed bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = req.getInputStream();
        int read;
        byte[] data = new byte[256];
        while ((read = is.read(data, 0, data.length)) != -1) {
            baos.write(data, 0, read);
        }
    
        String pkcs7Response = new String(data);
        // we complete the PDF signing process
        byte[] sigbytes = Base64.decode(pkcs7Response);
        byte[] paddedSig = new byte[8192];
        System.arraycopy(sigbytes, 0, paddedSig, 0, sigbytes.length);
        PdfDictionary dic2 = new PdfDictionary();
        dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
        try {
            sap.close(dic2);
        } catch (DocumentException e) {
            throw new IOException(e);
        } 
    
        // we write the signed document to the HttpResponse output stream
        byte[] pdf = os.toByteArray();
        OutputStream sos = resp.getOutputStream();
        sos.write(pdf, 0, pdf.length);
        sos.flush();
        sos.close();
    }
}
