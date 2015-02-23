package com.hightml.scanman.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
@Data
@Slf4j
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Scan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;



    String lastRelativeFilename; // relative from root of document repository (e.g. DropBox root)

    String ocrText = "";
    List<Keyword> containsKeywords;

    List<Category> categorySuggestions;


    LocalDateTime createdDate;



    public Scan(String lastRelativeFilename) {
        this.lastRelativeFilename = lastRelativeFilename;
        File f = new File(lastRelativeFilename);


    }


}
