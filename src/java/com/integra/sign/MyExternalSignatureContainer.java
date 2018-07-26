/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.sign;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.security.ExternalSignatureContainer;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;

/**
 *
 * @author Vidya
 */
public class MyExternalSignatureContainer implements ExternalSignatureContainer {

        protected byte[] sig;
        protected Certificate[] chain;

        public MyExternalSignatureContainer(byte[] sig, Certificate[] chain) {
            this.sig = sig;
            this.chain = chain;
        }

        public byte[] sign(InputStream is) throws GeneralSecurityException {

            return sig;
        }

    @Override
    public void modifySigningDictionary(PdfDictionary pd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }
