package com.hightml.scanman.processing;

/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 03/03/15
 * Time: 22:27
 * <p>
 * Copyright by HighTML.
 */
public class ProcessingException  extends RuntimeException {

    public ProcessingException(final String message) {
        super(message);
    }


    public ProcessingException(final String message, final Throwable cause) {
        super(message, cause);
    }

}


