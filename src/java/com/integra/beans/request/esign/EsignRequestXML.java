package com.integra.beans.request.esign;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.log4j.Logger;

public class EsignRequestXML {

    private static Logger log = Logger.getLogger("ASP");
    
    public static String generateEsignRequestXML(Esign esign) {

        StringWriter stringWriter = new StringWriter();

        try {
            // System.out.println("------- E-Sign Request XML -----------\n");
            JAXBContext jaxbContext = JAXBContext.newInstance(Esign.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(esign, stringWriter);
        } catch (JAXBException e) {
//            e.printStackTrace();
            log.error(e.getMessage());
        }

        return stringWriter.toString();
    }
}
