package com.hightml.scanman.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static com.hightml.scanman.Application.*;

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

    @Column(unique = true)
    String digest;

    @Transient
    Path file;

    String ocrText = "";

    @OneToMany(targetEntity = Keyword.class, fetch = FetchType.EAGER)
    List<Keyword> containsKeywords;


    @OneToMany(targetEntity = Category.class, fetch = FetchType.EAGER)
    List<Category> categorySuggestions;


    LocalDateTime scanCreatedDate;


    LocalDateTime scanProcessedDate;

    int nrPages;


    private boolean flattenedToImageFile;


    public Scan(String lastRelativeFilename) {
        this.lastRelativeFilename = lastRelativeFilename;
        file =
                FileSystems.getDefault().getPath(SCAN_DIRECTORY, lastRelativeFilename);

    }

    public Scan(Path file) {
        this.lastRelativeFilename = file.toString();
        this.file = file;
    }



}
