package com.integra.controller;

import com.integra.dao.ASPDAO;
import com.integra.dao.ASPDAOImpl;
import com.integra.model.ActivityReport;
import com.integra.model.User;
import com.integra.utils.ConfigListener;
import com.integra.utils.PDFUtility;
import com.integra.utils.ValidateSession;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.codec.Base64;
import com.nsdl.esign.preverifiedNo.controller.EsignApplication;
import java.io.ByteArrayOutputStream;
import org.apache.struts.actions.DispatchAction;
import com.integra.utils.ASPConstants;
import com.integra.utils.UtilMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class ASPRequest
 */
public class ESignController extends DispatchAction {

    private static final Logger log = Logger.getLogger(ESignController.class);
    private static final String ALGORITHM = "SHA256";
    private static final String RESPONSE = "response";
    private static final String INVALID_SESSION = "Inavlid Session";
    private static final String DOWNLOADFILENAME = "downloadFileName";
    private static final String APPLICATION_OCTECT_STREAM = "application/octet-stream";
    private static final String TXN_REFNO = "txnrefno";
    private static final String ESIGN_STATUS = "esignStatus";
    private static final String FAILURE_REASON = "failureReason"; //failureReason
    private static final String PKCS7RESPONSE = "pkcs7Response";
    private static final String USERX509CERTIFICATE = "userX509Certificate";
    private static final String SUCCESS = "Success";
    private static final String FAILURE = "Failure";
    private static final long serialVersionUID = 1L;
    static String ts;
    static String uid;

    public ESignController() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void geteSignRequestXml(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        PrintWriter writer = response.getWriter();

        String aadhaar;
        String pdfReadServerPath;
        String aspId = "ASPIMSPLMUMTEST198";
        String authMode = "1";
        String responseUrl;
        String p12CertificatePath;
        String p12CertiPwd;
        String tickImagePath;
        int serverTime;
        String alias;
        int pageNumberToInsertSignatureStamp;
        String nameToShowOnSignatureStamp;
        String locationToShowOnSignatureStamp;
        String reasonForSign;
        int xCo_ordinates;
        int yCo_ordinates;
        int signatureWidth;
        int signatureHeight;
        String pdfPassword;
        response.setContentType("text/javascript");
        JSONObject respjsonobj = new JSONObject();

        if (!ValidateSession.validateUserSession(request)) {
            request.getSession().invalidate();
            respjsonobj.put(RESPONSE, INVALID_SESSION);
        } else {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                try {
                    FileItemFactory factory = (FileItemFactory) new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List items = upload.parseRequest(request);
                    Iterator iter = items.iterator();
                    File[] file = new File[items.size()];
                    int i = 0;

                    Calendar currentDat = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
                    Date currentDate = currentDat.getTime();
                    String strCurrentDate = dateFormat.format(currentDate);

                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();

                        if (!item.isFormField()) {

                            file[i] = new File(ConfigListener.aspConf.getUploadfilepath() + File.separator + strCurrentDate + "#" + item.getName());
                            item.write(file[i]);

                            pdfReadServerPath = file[i].getAbsolutePath();
                            i++;
                        } else {
                            if (item.getFieldName().equals("tbAdhar")) {
                                aadhaar = item.getString();
                            }
                            if (item.getFieldName().equals("tbAdhar")) {
                                aadhaar = item.getString();
                            }
                        }
                        item.delete();
                    }

                } catch (Exception ex) {
                    log.error("Error " + ex.getMessage());
                    respjsonobj.put(RESPONSE, "Exception occured");
                }
            }
        }
        JSONArray responseJsonArray = new JSONArray();
        responseJsonArray.put(respjsonobj);
        writer.println(responseJsonArray);
        writer.close();
    }

    public void generateUnsignedEsignRequestXml(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String fileName = null;

        PrintWriter writer = response.getWriter();
        response.setContentType("text/javascript");
        JSONObject respjsonobj = new JSONObject();

        if (!ValidateSession.validateUserSession(request)) {
            request.getSession().invalidate();
            respjsonobj.put(RESPONSE, INVALID_SESSION);
        } else {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                try {
                    FileItemFactory factory = (FileItemFactory) new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List items = upload.parseRequest(request);
                    Iterator iter = items.iterator();
                    File[] file = new File[items.size()];
                    int i = 0;

                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();

                        if (!item.isFormField()) {

//                            file[i] = new File(ConfigListener.aspConf.getUploadfilepath() + File.separator + strCurrentDate + "#" + item.getName());
                            file[i] = new File(ConfigListener.aspConf.getUploadfilepath() + File.separator + item.getName());
                            item.write(file[i]);
                            fileName = file[i].getAbsolutePath();
                            i++;
                        }
//                        item.delete();
                    }


                    EsignApplication esignApplication = new EsignApplication();
                    String unsignedXML = esignApplication.generateUnsignedEsignRequestXml("", fileName, "ASPIMSPLMUMTEST198", ASPConstants.ESIGN_AUTHMODE, ConfigListener.aspConf.getAsp_webservice_uri() + "/nsdlresp", "D:\\VIDYA\\working projects\\2016\\september\\documents\\images\\tickimage.png", 10, 1, "Vidya", "Bangalore", "Test Sign", 40, 60, 150, 50, null, UtilMethods.getTXNId(), ASPConstants.ESIGN_RESP_SIGN_TYPE);
                    System.out.println("unsignedXML " + unsignedXML);

                    respjsonobj.put(RESPONSE, unsignedXML);
//
                    HttpSession session = request.getSession();
                    session.setAttribute(DOWNLOADFILENAME, fileName);
//                    session.setAttribute("sap", respObj.get("sap"));
//                    session.setAttribute("baos", respObj.get("baos"));

                } catch (Exception ex) {
                    log.error("Error " + ex.getMessage());
                    respjsonobj.put(RESPONSE, "Exception occured");
                }
//                finally {
//
//                    File uploadDir = new File(ConfigListener.aspConf.getUploadfilepath());
//                    File[] uploadedFiles = uploadDir.listFiles();
//                    for (File uploadFile : uploadedFiles) {
//                        boolean isDeleted = uploadFile.delete();
//                        if (isDeleted) {
//                            log.debug("File deleted : " + uploadFile);
//                        }
//                    }
//                }
            }
        }
        JSONArray responseJsonArray = new JSONArray();
        responseJsonArray.put(respjsonobj);
        writer.println(responseJsonArray);
        writer.close();
    }

    public void signDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("In signDoc method..");
//        {
//	"esignStatus": "1",
//	"userX509Certificate": "MIIE7jCCA9agAwIBAgIDAQNbMA0GCSqGSIb3DQEBCwUAMH4xCzAJBgNVBAYTAklO\nMRQwEgYDVQQIDAtNYWhhcmFzaHRyYTENMAsGA1UEBwwEUFVORTEOMAwGA1UECgwF\nQy1EQUMxIjAgBgNVBAsMGVRlc3QgQ2VydGlmeWluZyBBdXRob3JpdHkxFjAUBgNV\nBAMMDVRlc3QgQy1EQUMgQ0EwHhcNMTgwNDAyMTIwNDM0WhcNMTgwNDAyMTIzNDM0\nWjCB2TEOMAwGA1UEBhMFSW5kaWExEjAQBgNVBAgTCVN0YXRlIFR3bzERMA8GA1UE\nChMIUGVyc29uYWwxGDAWBgNVBAMTD1BlcnNvbiBUd28gTmFtZTEPMA0GA1UEERMG\nOTAwMDAyMUowSAYDVQQtA0EAMmMwMzNkZTE1MjRkOTUwZDNmYTA5MjdmMDU0ZDc1\nMDAwZDAxYjc5MTNlMGI5MzQ3OWVhYjRhYjRkMjVlMDJmNzEpMCcGA1UEQRMgYzZk\nNGE4NTRmZTdkNGI1MGE0OTg0ZjIyNzAwMTY3ZmEwWTATBgcqhkjOPQIBBggqhkjO\nPQMBBwNCAARK42rdGZn3POfbIr3m74KrWqYNnkW5V4bNlIFf8vPnOTyYwfhZeL0G\nMSn3EIUP9scPnS7iwBG3Mig0YXC0OpBdo4IB4jCCAd4wCQYDVR0TBAIwADAdBgNV\nHQ4EFgQUO799wN5EGIvTLeD3s9DRRyrQu1AwHwYDVR0jBBgwFoAUDnyhldbazCDz\nKE+UvXfTofQwixYwDgYDVR0PAQH/BAQDAgbAMDkGA1UdHwQyMDAwLqAsoCqGKGh0\ndHBzOi8vZXNpZ24uY2RhYy5pbi9jYS9lc2lnbkNBMjAxNC5jcmwwggFEBgNVHSAE\nggE7MIIBNzCCAQEGB2CCZGQBCQIwgfUwMAYIKwYBBQUHAgEWJGh0dHBzOi8vZXNp\nZ24uY2RhYy5pbi9jYS9DUFMvQ1BTLnBkZjCBwAYIKwYBBQUHAgIwgbMwPhY6Q2Vu\ndHJlIGZvciBEZXZlbG9wbWVudCBvZiBBZHZhbmNlZCBDb21wdXRpbmcgKEMtREFD\nKSwgUHVuZTAAGnFUaGlzIENQUyBpcyBvd25lZCBieSBDLURBQyBhbmQgdXNlcnMg\nYXJlIHJlcXVlc3RlZCB0byByZWFkIENQUyBiZWZvcmUgdXNpbmcgdGhlIEMtREFD\nIENBJ3MgY2VydGlmaWNhdGlvbiBzZXJ2aWNlczAwBgdggmRkAgQBMCUwIwYIKwYB\nBQUHAgIwFxoVQWFkaGFhciBlS1lDLU9UUCAyMDExMA0GCSqGSIb3DQEBCwUAA4IB\nAQAtW9D3aW2Vr4z5hneUwD+5VdzoUS4lmU26FQub5NyzHuuY8YjjzYwfvH3lAnnS\n2+ylyzzcAnT5HdqMiIn8SUxUYj15K55xEhK9fM9y/vRMypm+RNuWbT9vY/IPwycP\nAjFV2axbkBrVVMK5Rgdz/eQ21JOGEEzYpJexab60JdaB2Rx+5SL+QMbb4gtSCO7y\niFpySb5zGlgVNpaMLJfGRui55sQW/y0FbBXBHQERzYyOFShJBDo5QgxGOYLxVLZw\nB0En6wkXsgh3MOnyVlX8OVhcBLUjUlnwAeu83rRX5tqzmjDgSu+sjJQhTmS0PEGK\nS+dJT4RN2VX6uv3pKeN9yXQH\n",
//	"failureReason": "NA",
//	"pkcs7Response": "MIIKYQYJKoZIhvcNAQcCoIIKUjCCCk4CAQExDzANBglghkgBZQMEAgEFADALBgkqhkiG9w0BBwGg\nggjFMIIDzzCCAregAwIBAgIJAPjGKCX3TQcFMA0GCSqGSIb3DQEBBQUAMH4xCzAJBgNVBAYTAklO\nMRQwEgYDVQQIDAtNYWhhcmFzaHRyYTENMAsGA1UEBwwEUFVORTEOMAwGA1UECgwFQy1EQUMxIjAg\nBgNVBAsMGVRlc3QgQ2VydGlmeWluZyBBdXRob3JpdHkxFjAUBgNVBAMMDVRlc3QgQy1EQUMgQ0Ew\nHhcNMTYxMjEzMDg0MzQ2WhcNMjYxMjExMDg0MzQ2WjB+MQswCQYDVQQGEwJJTjEUMBIGA1UECAwL\nTWFoYXJhc2h0cmExDTALBgNVBAcMBFBVTkUxDjAMBgNVBAoMBUMtREFDMSIwIAYDVQQLDBlUZXN0\nIENlcnRpZnlpbmcgQXV0aG9yaXR5MRYwFAYDVQQDDA1UZXN0IEMtREFDIENBMIIBIjANBgkqhkiG\n9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0MWSA0jiO4YINrvyONf3kfbF3fYr2zjgZOJJ80LiCdEC+ZTA\nDzBr0AdKcOl4i64D+xvo7yUH5h78rFi6jnMDWE7sEGyq3RZf+dfA6ljqC2gdKEeHOMN9DYOy/j+P\nk5CgY4CMShYPb2W4kSlq7v5MaC8mZBul+ByF8W/Wp60VOwQzWi3z4W+ItnKtztJ4LYKEQUXopiNl\nUSt1UZW80P1dTG9CWYiG3kU+Q21/TeKROoMTEO5kQFO31hHK7rETmL2/qWoAuGUWGL6avUH8+SS/\nu3s0teq/v0O9N7xMpzcV96deEnKS/THtEuiIHsa3B7CDtVAtVDBooM27sm2knYDCtwIDAQABo1Aw\nTjAdBgNVHQ4EFgQUDnyhldbazCDzKE+UvXfTofQwixYwHwYDVR0jBBgwFoAUDnyhldbazCDzKE+U\nvXfTofQwixYwDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQUFAAOCAQEAGNH1EnjmtFCQCyEhO5vU\n43xtXsxWnZyv7D1hPaIVTCo8UfeeQvrwjrCs1OsZOIdYDT4GZPXYi0FVeEfUqFWiFQ8+Zaeqcanf\nMC182v414UPqWMLjziVgSbtOnzyGs22jAsTGdollyLSwH3kh5OvXU9PQZgfgZPLsXVEBZzNDkRCm\nHOyNQDnSGwVtBKOT/aP7Y7nP8kWiuHQ3/3yhgxPsTSLAiBwQS3VpCcH0g8VMMYRlGOOFiTaB2/TJ\n5xDtGlYN3S9nniGyaO3yJOYklzHfP37PFccQkoa1kNUU7dFte7dgbVhlaz7NgSlOIAHG/wbrJ2j4\nwcnkvYSEF4aV7DHolTCCBO4wggPWoAMCAQICAwEDWzANBgkqhkiG9w0BAQsFADB+MQswCQYDVQQG\nEwJJTjEUMBIGA1UECAwLTWFoYXJhc2h0cmExDTALBgNVBAcMBFBVTkUxDjAMBgNVBAoMBUMtREFD\nMSIwIAYDVQQLDBlUZXN0IENlcnRpZnlpbmcgQXV0aG9yaXR5MRYwFAYDVQQDDA1UZXN0IEMtREFD\nIENBMB4XDTE4MDQwMjEyMDQzNFoXDTE4MDQwMjEyMzQzNFowgdkxDjAMBgNVBAYTBUluZGlhMRIw\nEAYDVQQIEwlTdGF0ZSBUd28xETAPBgNVBAoTCFBlcnNvbmFsMRgwFgYDVQQDEw9QZXJzb24gVHdv\nIE5hbWUxDzANBgNVBBETBjkwMDAwMjFKMEgGA1UELQNBADJjMDMzZGUxNTI0ZDk1MGQzZmEwOTI3\nZjA1NGQ3NTAwMGQwMWI3OTEzZTBiOTM0NzllYWI0YWI0ZDI1ZTAyZjcxKTAnBgNVBEETIGM2ZDRh\nODU0ZmU3ZDRiNTBhNDk4NGYyMjcwMDE2N2ZhMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAESuNq\n3RmZ9zzn2yK95u+Cq1qmDZ5FuVeGzZSBX/Lz5zk8mMH4WXi9BjEp9xCFD/bHD50u4sARtzIoNGFw\ntDqQXaOCAeIwggHeMAkGA1UdEwQCMAAwHQYDVR0OBBYEFDu/fcDeRBiL0y3g97PQ0Ucq0LtQMB8G\nA1UdIwQYMBaAFA58oZXW2swg8yhPlL1306H0MIsWMA4GA1UdDwEB/wQEAwIGwDA5BgNVHR8EMjAw\nMC6gLKAqhihodHRwczovL2VzaWduLmNkYWMuaW4vY2EvZXNpZ25DQTIwMTQuY3JsMIIBRAYDVR0g\nBIIBOzCCATcwggEBBgdggmRkAQkCMIH1MDAGCCsGAQUFBwIBFiRodHRwczovL2VzaWduLmNkYWMu\naW4vY2EvQ1BTL0NQUy5wZGYwgcAGCCsGAQUFBwICMIGzMD4WOkNlbnRyZSBmb3IgRGV2ZWxvcG1l\nbnQgb2YgQWR2YW5jZWQgQ29tcHV0aW5nIChDLURBQyksIFB1bmUwABpxVGhpcyBDUFMgaXMgb3du\nZWQgYnkgQy1EQUMgYW5kIHVzZXJzIGFyZSByZXF1ZXN0ZWQgdG8gcmVhZCBDUFMgYmVmb3JlIHVz\naW5nIHRoZSBDLURBQyBDQSdzIGNlcnRpZmljYXRpb24gc2VydmljZXMwMAYHYIJkZAIEATAlMCMG\nCCsGAQUFBwICMBcaFUFhZGhhYXIgZUtZQy1PVFAgMjAxMTANBgkqhkiG9w0BAQsFAAOCAQEALVvQ\n92ltla+M+YZ3lMA/uVXc6FEuJZlNuhULm+Tcsx7rmPGI482MH7x95QJ50tvspcs83AJ0+R3ajIiJ\n/ElMVGI9eSuecRISvXzPcv70TMqZvkTblm0/b2PyD8MnDwIxVdmsW5Aa1VTCuUYHc/3kNtSThhBM\n2KSXsWm+tCXWgdkcfuUi/kDG2+ILUgju8ohackm+cxpYFTaWjCyXxkbouebEFv8tBWwVwR0BEc2M\njhUoSQQ6OUIMRjmC8VS2cAdBJ+sJF7IIdzDp8lZV/DlYXAS1I1JZ8AHrvN60V+bas5ow4ErvrIyU\nIU5ktDxBikvnSU+ETdlV+rr96Snjfcl0BzGCAWAwggFcAgEBMIGFMH4xCzAJBgNVBAYTAklOMRQw\nEgYDVQQIDAtNYWhhcmFzaHRyYTENMAsGA1UEBwwEUFVORTEOMAwGA1UECgwFQy1EQUMxIjAgBgNV\nBAsMGVRlc3QgQ2VydGlmeWluZyBBdXRob3JpdHkxFjAUBgNVBAMMDVRlc3QgQy1EQUMgQ0ECAwED\nWzANBglghkgBZQMEAgEFAKBpMBgGCSqGSIb3DQEJAzELBgkqhkiG9w0BBwEwHAYJKoZIhvcNAQkF\nMQ8XDTE4MDQwMjEyMDQzNFowLwYJKoZIhvcNAQkEMSIEIFh9gqQbqRg1crA+l3Dxf8sTT9MGNbh6\nAJEFsf1SJfgjMAwGCCqGSM49BAMCBQAERzBFAiAQcoeKPXhpiqEcI/carsrhIcE5RGf+L7Fc4RNJ\nxxrMNQIhALRb0vT9dIRtMmJtKRVvARz+SnnVwEliWKoADpdEPBZD",
//	"txnrefno": "0a267de2-92a7-4cfe-9cdd-684f63728b6f"
//        }

        JSONObject respjsonobj = new JSONObject();
        PrintWriter writer = response.getWriter();

        if (!ValidateSession.validateUserSession(request)) {
            request.getSession().invalidate();
            respjsonobj.put(RESPONSE, INVALID_SESSION);
        } else {
            response.setContentType(APPLICATION_OCTECT_STREAM);
            // we get the objects we need for postsigning from the session
            HttpSession session = request.getSession(false);

            PdfSignatureAppearance sap = (PdfSignatureAppearance) session.getAttribute("sap");
            FileOutputStream os = (FileOutputStream) session.getAttribute("baos");

            // we read the signed bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream is = request.getInputStream();
            int read;
            byte[] data = new byte[8192];
            while ((read = is.read(data, 0, data.length)) != -1) {
                baos.write(data, 0, read);
            }

            String responseData = new String(data).trim();
            log.info("responseData " + responseData);
            // we complete the PDF signing process
            JSONObject responseDataObject = new JSONObject(responseData);
            //{"esignStatus":"0","userX509Certificate":"","pkcs7Response":"","txnrefno":"469f859f-fd25-4c41-9ecf-1f2785264a21"}
            String txnrefno = responseDataObject.getString(TXN_REFNO);
            String esignStatus = responseDataObject.getString(ESIGN_STATUS);
            String failureReason = responseDataObject.getString(FAILURE_REASON);
            String pkcs7Response = responseDataObject.getString(PKCS7RESPONSE);
            String userX509Certificate = responseDataObject.getString(USERX509CERTIFICATE);

            if (esignStatus.equalsIgnoreCase("1")) {
                respjsonobj.put(RESPONSE, SUCCESS);
                respjsonobj.put(TXN_REFNO, txnrefno);
                respjsonobj.put(ESIGN_STATUS, esignStatus);
                respjsonobj.put(FAILURE_REASON, failureReason);
                respjsonobj.put(USERX509CERTIFICATE, userX509Certificate);

                EsignApplication esignApplication = new EsignApplication();
                String downloadFilePath = (String) request.getSession().getAttribute(DOWNLOADFILENAME);
//                    String unsignedXML = esignApplication.generateUnsignedEsignRequestXml("", fileName, "ASPIMSPLMUMTEST198", ASPConstants.ESIGN_AUTHMODE, ConfigListener.aspConf.getAsp_webservice_uri() + "/nsdlresp", "D:\\VIDYA\\working projects\\2016\\september\\documents\\images\\tickimage.png", 10, 1, "Vidya", "Bangalore", "Test Sign", 40, 60, 150, 50, null, UtilMethods.getTXNId(), ASPConstants.ESIGN_RESP_SIGN_TYPE);

                esignApplication.getSignOnDocument(pkcs7Response, downloadFilePath, "D:\\VIDYA\\working projects\\2016\\september\\documents\\images\\tickimage.png", 10, 1, "Vani","Hospet", "Sum sumne", 40,60,150, 50, null, downloadFilePath);
                byte[] sigbytes = Base64.decode(pkcs7Response);
                byte[] paddedSig = new byte[8192];
                System.arraycopy(sigbytes, 0, paddedSig, 0, sigbytes.length);
                PdfDictionary dic2 = new PdfDictionary();
                dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
                sap.close(dic2);
            } else {
                respjsonobj.put(RESPONSE, FAILURE);
                respjsonobj.put(TXN_REFNO, txnrefno);
                respjsonobj.put(ESIGN_STATUS, esignStatus);
                respjsonobj.put(FAILURE_REASON, failureReason);
                respjsonobj.put(USERX509CERTIFICATE, userX509Certificate);
            }

            request.getSession().setAttribute(TXN_REFNO, txnrefno);
            request.getSession().setAttribute(ESIGN_STATUS, esignStatus);
            request.getSession().setAttribute(FAILURE_REASON, failureReason);
            boolean isInserted = inserteSignDeatils(mapping, form, request, response);
        }

        JSONArray responseJsonArray = new JSONArray();
        responseJsonArray.put(respjsonobj);
        writer.println(responseJsonArray);
        writer.close();

//        return mapping.findForward(strForward);
    }

    public boolean inserteSignDeatils(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean isInserted = false;
        if (!ValidateSession.validateUserSession(request)) {
            request.getSession().invalidate();
        } else {
            User user = (User) request.getSession().getAttribute("user");
            int userId = user.getUserId();
            String downloadFilePath = (String) request.getSession().getAttribute(DOWNLOADFILENAME);
            String doc_name = new File(downloadFilePath).getName();
            String txnrefno = (String) request.getSession().getAttribute(TXN_REFNO);
            String esignStatus = (String) request.getSession().getAttribute(ESIGN_STATUS);
            String failureReason = (String) request.getSession().getAttribute(FAILURE_REASON);

            ActivityReport activityReport = new ActivityReport();
            activityReport.setUserId(userId);
            activityReport.setDocName(doc_name);
            activityReport.setTransactionRefNo(txnrefno);
            activityReport.setTransactionDate(new Date());
            activityReport.setEsignStatus(esignStatus);
            activityReport.setFailureReason(failureReason);
            activityReport.setIsDownloaded("No");
            ASPDAO aspdao = new ASPDAOImpl();
            isInserted = aspdao.insertAcativityReport(activityReport);
            if (isInserted) {
                log.info("Inserted into Activity report");
            } else {
                log.info("Insertion into Activity report failed");
            }
        }
        return isInserted;
    }

    public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType(APPLICATION_OCTECT_STREAM);
        HttpSession session = request.getSession(false);

        if (!ValidateSession.validateUserSession(request)) {
            request.getSession().invalidate();
            //respjsonobj.put(RESPONSE, INVALID_SESSION);
        }

        String downloadFilePath = (String) session.getAttribute(DOWNLOADFILENAME);
        log.info("downloadFilePath " + downloadFilePath);
        String downloadFileName = new File(downloadFilePath).getName();
        log.info("downloadFileName " + downloadFileName);
        response.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
        FileInputStream in = null;
        ServletOutputStream out = null;
        try {
            in = new FileInputStream(new File(ConfigListener.aspConf.getTempfilepath() + File.separator + downloadFileName));
            out = response.getOutputStream();
            int size = in.available();
            byte[] outputByte = new byte[size];
            //copy binary content to output stream
            while (in.read(outputByte, 0, size) != -1) {
                out.write(outputByte, 0, size);
            }
            out.flush();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }

        }

        return null;
    }

    public void error(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In signDoc method..");
//       <EsignResp 
//          errCode='112' 
//          errMsg='Aadhaar number does not have both email and mobile' 
//          resCode='342ad073-0792-4a5b-b5cf-7ebed6d919ef' 
//          status='0' ts='2018-04-19T14:37:53.390' 
//          txn='55033bfb-e29a-494e-ae08-8d5b21a4f182'>
//       </EsignResp>

        JSONObject respjsonobj = new JSONObject();
        PrintWriter writer = response.getWriter();

        if (!ValidateSession.validateUserSession(request)) {
            request.getSession().invalidate();
            respjsonobj.put(RESPONSE, INVALID_SESSION);
        } else {
            response.setContentType(APPLICATION_OCTECT_STREAM);
        }
    }
}