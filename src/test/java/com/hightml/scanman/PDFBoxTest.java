package com.hightml.scanman;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.lucene.LucenePDFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 14/02/15
 * Time: 11:17
 * <p>
 * Copyright by HighTML.
 */
@Slf4j
public class PDFBoxTest {

    @Test
    public void testStripper() throws Exception {
        File file = new File("src/test/resources/sample-scan-ocr.pdf");

        String ocrText = "";

        try {
            PDDocument document = PDDocument.load(file);
            document.getClass();
            if (document.isEncrypted()) {
                document.decrypt("");
            }


            PDFTextStripper stripper = new PDFTextStripper();
            ocrText = stripper.getText(document);

        } catch (CryptographyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("OCR = {}", ocrText);


    }


    @Test
    public void testLucene() throws Exception {
        File file = new File("src/test/resources/sample-scan-ocr.pdf");

        String ocrText = "";

//        Document luceneDocument = LucenePDFDocument.getDocument(file);


    }
}
