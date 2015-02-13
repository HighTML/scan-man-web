package com.hightml.scanman.value;

import com.hightml.scanman.value.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 13/02/15
 * Time: 18:04
 * <p>
 * Copyright by HighTML.
 */
@Getter
@Setter
@Slf4j
public class Scan {


    File file;
    String absoluteFilename;
    String ocrText = "";
    List<String> keywordSuggestions;

    List<Category> categorySuggestions;


    public Scan(String filename) {
        file = new File(filename);
        log.debug(file.getAbsolutePath());
        absoluteFilename = file.getAbsolutePath();
        ocrText = "Hellodeug eiuewyh ";
        keywordSuggestions = new ArrayList<>();
        keywordSuggestions.add("hypotheek");
    }

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
}
