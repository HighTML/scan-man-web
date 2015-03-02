package com.hightml.scanman.cannibals;

import lombok.Value;

import java.util.Arrays;

/**
 * Class that represents a file
 */
@Value
@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
public class Document {



    /**
     * The filename of the Document
     */
    private String filename;

    /**
     * The data of the Document as a byte[].
     */
    private byte[] data;

    public Document(final String filename, final Integer index, final byte[] fileData) {
        this.filename = filename;
        //copy-the-byte-array to prevent altering of array.
        if (fileData == null) {
            this.data = null;
        } else {
            this.data = Arrays.copyOf(fileData, fileData.length);
        }
    }
}
