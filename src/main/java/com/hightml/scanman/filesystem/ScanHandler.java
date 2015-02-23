package com.hightml.scanman.filesystem;

import com.hightml.scanman.value.Scan;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 22/02/15
 * Time: 13:53
 * <p>
 * Copyright by HighTML.
 */
@Component
@NoArgsConstructor
public class ScanHandler {

    public static List<Scan> findAll() {
        List<Scan> scans = new ArrayList<>();
        scans.add(new Scan("src/test/resources/sample-scan.pdf"));
        return scans;
    }


    public static Scan getOne(String f) {
        Scan scan = new Scan("src/test/resources/sample-scan.pdf");
        //scan.readText();

        return scan;
    }


    /*

    public Scan readText() {
        try {
            PDDocument document = PDDocument.load(file);
            document.getClass();
            if (document.isEncrypted()) {
                document.decrypt("");
            }


            PDFTextStripper stripper = new PDFTextStripper();
            ocrText = stripper.getText(document);

            return this;
        } catch (CryptographyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    */
}
