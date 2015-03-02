package com.hightml.scanman.processing;

import com.hightml.scanman.value.Scan;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 01/03/15
 * Time: 11:43
 * <p>
 * Copyright by HighTML.
 */
@Component
public class ScanToTextConverter {

    public Scan addOcrText(Scan scan) {
        try {

            PDDocument document = PDDocument.load(Files.newInputStream(scan.getFile()));
            document.getClass();

            PDFTextStripper stripper = new PDFTextStripper();
            String ocrText = stripper.getText(document);

            if (StringUtils.isBlank(ocrText)) {
                ocrText = generateOcrText(scan);
            }

            ocrText = clean(ocrText);

            scan.setOcrText(ocrText);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return scan;
    }

    private String clean(String ocrText) {
        return ocrText; // TODO remove extra whitespace, and remove formatting shit
    }

    private String generateOcrText(Scan scan) throws TesseractException {
        Tesseract instance = Tesseract.getInstance();  // JNA Interface Mapping
        // Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping

        String result = instance.doOCR(scan.getFile().toFile());
        System.out.println(result);
        return result;

    }

    public static void mainx(String[] a) {

//        System.load("/usr/local/Cellar/tesseract/3.02.02_3/libtesseract.dylib");
//        System.load("/usr/local/Cellar/tesseract/3.02.02_3/libtesseract.3.dylib");

        File imageFile = new File("test/resources/eurotext.tif");
        Tesseract instance = Tesseract.getInstance();

//In case you don't have your own tessdata, let it also be extracted for you
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");

//Set the tessdata path
        instance.setDatapath(tessDataFolder.getAbsolutePath());

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}
