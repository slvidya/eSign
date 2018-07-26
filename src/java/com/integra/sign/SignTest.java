/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.sign;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalBlankSignatureContainer;
import com.itextpdf.text.pdf.security.ExternalSignatureContainer;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Vidya
 */
public class SignTest {

    public byte[] emptySignature_hash(String src, String dest, String fieldname) throws IOException, DocumentException, GeneralSecurityException {
        PdfReader reader = new PdfReader(src);
        FileOutputStream os = new FileOutputStream(dest);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        appearance.setVisibleSignature(new Rectangle(36, 748, 144, 780), 1, fieldname);
        appearance.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
        ExternalSignatureContainer external = new ExternalBlankSignatureContainer(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
        MakeSignature.signExternalContainer(appearance, external, 8192);

        InputStream inp = appearance.getRangeStream();

        BouncyCastleDigest digest = new BouncyCastleDigest();

        byte[] hash = DigestAlgorithms.digest(inp, digest.getMessageDigest("SHA256"));
        return hash;

    }

    public void createSignature(String src, String dest, String fieldname, byte[] hash, Certificate[] chain) throws IOException, DocumentException, GeneralSecurityException {

        PdfReader reader = new PdfReader(src);
        FileOutputStream os = new FileOutputStream(dest);
        ExternalSignatureContainer external = new MyExternalSignatureContainer(hash, chain);
        MakeSignature.signDeferred(reader, fieldname, os, external);
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException, DocumentException {

        SignTest app = new SignTest();
        String SRC = "D:\\VIDYA\\working projects\\2016\\september\\documents\\CDAC Error Codes For ASP.pdf";
        String DEST = "D:\\VIDYA\\working projects\\2016\\september\\documents\\CDAC Error Codes For ASP_Signed.pdf";
        String TEMP = "D:\\VIDYA\\working projects\\2016\\september\\documents\\CDAC Error Codes For ASP_TEMP.pdf";

        byte[] hh = app.emptySignature_hash(SRC, TEMP, "sig1");
        byte[] hh_sign = app.signed_hash();
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        CertificateFactory certFactory = CertificateFactory.getInstance("X509", "BC");
        InputStream is = new ByteArrayInputStream(hh_sign);
        X509Certificate cert = (X509Certificate) certFactory.generateCertificate(is);
        Certificate[] chain = new Certificate[1];
        chain[0] = cert;
        app.createSignature(TEMP, DEST, "sig1", hh_sign, chain);

    }

    public byte[] signed_hash() throws GeneralSecurityException {
        String pkcs7sig = "MIIKYQYJKoZIhvcNAQcCoIIKUjCCCk4CAQExDzANBglghkgBZQMEAgEFADALBgkqhkiG9w0BBwGg\n"
                + "ggjFMIIDzzCCAregAwIBAgIJAPjGKCX3TQcFMA0GCSqGSIb3DQEBBQUAMH4xCzAJBgNVBAYTAklO\n"
                + "MRQwEgYDVQQIDAtNYWhhcmFzaHRyYTENMAsGA1UEBwwEUFVORTEOMAwGA1UECgwFQy1EQUMxIjAg\n"
                + "BgNVBAsMGVRlc3QgQ2VydGlmeWluZyBBdXRob3JpdHkxFjAUBgNVBAMMDVRlc3QgQy1EQUMgQ0Ew\n"
                + "HhcNMTYxMjEzMDg0MzQ2WhcNMjYxMjExMDg0MzQ2WjB+MQswCQYDVQQGEwJJTjEUMBIGA1UECAwL\n"
                + "TWFoYXJhc2h0cmExDTALBgNVBAcMBFBVTkUxDjAMBgNVBAoMBUMtREFDMSIwIAYDVQQLDBlUZXN0\n"
                + "IENlcnRpZnlpbmcgQXV0aG9yaXR5MRYwFAYDVQQDDA1UZXN0IEMtREFDIENBMIIBIjANBgkqhkiG\n"
                + "9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0MWSA0jiO4YINrvyONf3kfbF3fYr2zjgZOJJ80LiCdEC+ZTA\n"
                + "DzBr0AdKcOl4i64D+xvo7yUH5h78rFi6jnMDWE7sEGyq3RZf+dfA6ljqC2gdKEeHOMN9DYOy/j+P\n"
                + "k5CgY4CMShYPb2W4kSlq7v5MaC8mZBul+ByF8W/Wp60VOwQzWi3z4W+ItnKtztJ4LYKEQUXopiNl\n"
                + "USt1UZW80P1dTG9CWYiG3kU+Q21/TeKROoMTEO5kQFO31hHK7rETmL2/qWoAuGUWGL6avUH8+SS/\n"
                + "u3s0teq/v0O9N7xMpzcV96deEnKS/THtEuiIHsa3B7CDtVAtVDBooM27sm2knYDCtwIDAQABo1Aw\n"
                + "TjAdBgNVHQ4EFgQUDnyhldbazCDzKE+UvXfTofQwixYwHwYDVR0jBBgwFoAUDnyhldbazCDzKE+U\n"
                + "vXfTofQwixYwDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQUFAAOCAQEAGNH1EnjmtFCQCyEhO5vU\n"
                + "43xtXsxWnZyv7D1hPaIVTCo8UfeeQvrwjrCs1OsZOIdYDT4GZPXYi0FVeEfUqFWiFQ8+Zaeqcanf\n"
                + "MC182v414UPqWMLjziVgSbtOnzyGs22jAsTGdollyLSwH3kh5OvXU9PQZgfgZPLsXVEBZzNDkRCm\n"
                + "HOyNQDnSGwVtBKOT/aP7Y7nP8kWiuHQ3/3yhgxPsTSLAiBwQS3VpCcH0g8VMMYRlGOOFiTaB2/TJ\n"
                + "5xDtGlYN3S9nniGyaO3yJOYklzHfP37PFccQkoa1kNUU7dFte7dgbVhlaz7NgSlOIAHG/wbrJ2j4\n"
                + "wcnkvYSEF4aV7DHolTCCBO4wggPWoAMCAQICAwD/tjANBgkqhkiG9w0BAQsFADB+MQswCQYDVQQG\n"
                + "EwJJTjEUMBIGA1UECAwLTWFoYXJhc2h0cmExDTALBgNVBAcMBFBVTkUxDjAMBgNVBAoMBUMtREFD\n"
                + "MSIwIAYDVQQLDBlUZXN0IENlcnRpZnlpbmcgQXV0aG9yaXR5MRYwFAYDVQQDDA1UZXN0IEMtREFD\n"
                + "IENBMB4XDTE4MDMyMDA2MzA0OVoXDTE4MDMyMDA3MDA0OVowgdkxDjAMBgNVBAYTBUluZGlhMRIw\n"
                + "EAYDVQQIEwlTdGF0ZSBUd28xETAPBgNVBAoTCFBlcnNvbmFsMRgwFgYDVQQDEw9QZXJzb24gVHdv\n"
                + "IE5hbWUxDzANBgNVBBETBjkwMDAwMjFKMEgGA1UELQNBADJjMDMzZGUxNTI0ZDk1MGQzZmEwOTI3\n"
                + "ZjA1NGQ3NTAwMGQwMWI3OTEzZTBiOTM0NzllYWI0YWI0ZDI1ZTAyZjcxKTAnBgNVBEETIGM2ZDRh\n"
                + "ODU0ZmU3ZDRiNTBhNDk4NGYyMjcwMDE2N2ZhMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAESuNq\n"
                + "3RmZ9zzn2yK95u+Cq1qmDZ5FuVeGzZSBX/Lz5zk8mMH4WXi9BjEp9xCFD/bHD50u4sARtzIoNGFw\n"
                + "tDqQXaOCAeIwggHeMAkGA1UdEwQCMAAwHQYDVR0OBBYEFDu/fcDeRBiL0y3g97PQ0Ucq0LtQMB8G\n"
                + "A1UdIwQYMBaAFA58oZXW2swg8yhPlL1306H0MIsWMA4GA1UdDwEB/wQEAwIGwDA5BgNVHR8EMjAw\n"
                + "MC6gLKAqhihodHRwczovL2VzaWduLmNkYWMuaW4vY2EvZXNpZ25DQTIwMTQuY3JsMIIBRAYDVR0g\n"
                + "BIIBOzCCATcwggEBBgdggmRkAQkCMIH1MDAGCCsGAQUFBwIBFiRodHRwczovL2VzaWduLmNkYWMu\n"
                + "aW4vY2EvQ1BTL0NQUy5wZGYwgcAGCCsGAQUFBwICMIGzMD4WOkNlbnRyZSBmb3IgRGV2ZWxvcG1l\n"
                + "bnQgb2YgQWR2YW5jZWQgQ29tcHV0aW5nIChDLURBQyksIFB1bmUwABpxVGhpcyBDUFMgaXMgb3du\n"
                + "ZWQgYnkgQy1EQUMgYW5kIHVzZXJzIGFyZSByZXF1ZXN0ZWQgdG8gcmVhZCBDUFMgYmVmb3JlIHVz\n"
                + "aW5nIHRoZSBDLURBQyBDQSdzIGNlcnRpZmljYXRpb24gc2VydmljZXMwMAYHYIJkZAIEATAlMCMG\n"
                + "CCsGAQUFBwICMBcaFUFhZGhhYXIgZUtZQy1PVFAgMjAxMTANBgkqhkiG9w0BAQsFAAOCAQEAezZI\n"
                + "im6SAFxy6qeThiubXqaIgoE1XOj+z2Eu6Qdx49l1IHCDKYaX67SA8j8NieGE26bIlCFfQaNtg38B\n"
                + "FhiFyiiuWoF8YpMQzTmNgvPmuHeyn+3vjDD4K3t92u6gWrZUSHnk9OgW59CX6X2Sk6eUkabR1hS9\n"
                + "clNora49QwUu8C08etXcOarcRIcoWTL9sAeT3c8BEkdDJ3iRLmnLH743uILw67GeFaFTQsfTPD8S\n"
                + "lQsXN4DRyvdk7wtKzK4Xoict1vnlSHN9CDm9RUKTERyRLDn/8GNuoXU86WuHHnxwQekzatCh9X12\n"
                + "i4bqZfb+qD6Yb13JzRo0HUhpqNQxlxvtuDGCAWAwggFcAgEBMIGFMH4xCzAJBgNVBAYTAklOMRQw\n"
                + "EgYDVQQIDAtNYWhhcmFzaHRyYTENMAsGA1UEBwwEUFVORTEOMAwGA1UECgwFQy1EQUMxIjAgBgNV\n"
                + "BAsMGVRlc3QgQ2VydGlmeWluZyBBdXRob3JpdHkxFjAUBgNVBAMMDVRlc3QgQy1EQUMgQ0ECAwD/\n"
                + "tjANBglghkgBZQMEAgEFAKBpMBgGCSqGSIb3DQEJAzELBgkqhkiG9w0BBwEwHAYJKoZIhvcNAQkF\n"
                + "MQ8XDTE4MDMyMDA2MzA0OVowLwYJKoZIhvcNAQkEMSIEIJM7BV30FZovM8HGERkh91xiiDiSXZuo\n"
                + "c6dZBWt/Ha1jMAwGCCqGSM49BAMCBQAERzBFAiEA6ENDy++ftabDtjTbaxV3rROvgeW/nNW3ibDA\n"
                + "LRxYr1QCIAWda2if0kGoU1wYWvUkAVP0XvNxk28BHCJXMi23lZlj";
        byte[] ssig = Base64.decodeBase64(pkcs7sig.getBytes());
        return ssig;
    }

 /*   public byte[] signed_hash(byte[] hash, PrivateKey pk, Certificate[] chain) throws GeneralSecurityException {
        PrivateKeySignature signature = new PrivateKeySignature(pk, "SHA256", "SunPKCS11-eToken");
        BouncyCastleDigest digest = new BouncyCastleDigest();
        Calendar cal = Calendar.getInstance();
        String hashAlgorithm = signature.getHashAlgorithm();
        System.out.println(hashAlgorithm);
        PdfPKCS7 sgn = new PdfPKCS7(null, chain, "SHA256", null, digest, false);

        byte[] sh = sgn.getAuthenticatedAttributeBytes(hash, cal, null, null, CryptoStandard.CMS);
        byte[] extSignature = signature.sign(sh);

        System.out.println(signature.getEncryptionAlgorithm());
        sgn.setExternalDigest(extSignature, null, signature.getEncryptionAlgorithm());
        return sgn.getEncodedPKCS7(hash, cal, null, null, null, CryptoStandard.CMS);

    }*/
}
