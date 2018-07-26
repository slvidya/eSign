/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integra.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
//import com.lowagie.text.pdf.PdfReader;
//import com.lowagie.text.pdf.PdfSignatureAppearance;
//import com.lowagie.text.pdf.PdfStamper;

/**
 *
 * @author vidya
 */
public class ReadImage {
//    public static void main(String[] args) throws DocumentException, IOException
//    {
//        String imagePath = "C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert.jpg";
//       
//        String outFile ="E:\\VIDYA\\working projects\\2016\\september\\documents\\Desert.pdf";
//        createPdfFromImage(imagePath,outFile);
//    }
//    

    public static String createPdfFromImage(String imagePath, String filename) throws DocumentException, IOException {
        Image image = Image.getInstance(imagePath);
        // step 1
        //image.scaleToFit(new Rectangle(200, 200));
        image.scaleToFit(200, 200);
        //setAbsolutePosition(450f, 10f);
        Document document = new Document();
        // step 2
        // if file doesn't exist, then create
        FileOutputStream fos = new FileOutputStream(filename);

        PdfWriter writer = PdfWriter.getInstance(document, fos);

        // step 3
        document.open();
        // step 4
        //document.add(new Paragraph("Hello World!"));
        document.add(image);
        // step 5
        document.close();
        writer.close();
        fos.close();
        return filename;
    }
}
