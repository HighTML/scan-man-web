package com.hightml;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Please enter description here.
 * <p/>
 * User: marcel
 * Date: 13/02/15
 * Time: 18:04
 * <p/>
 * Copyright by HighTML.
 */
@Getter
@Setter
public class Scan {

    String absoluteFilename;
    String ocrText = "";
    List<String> keywordSuggestions;

    List<String> keyword;


}
