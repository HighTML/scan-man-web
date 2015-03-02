package com.hightml.scanman.cannibals.converters.exception;

/**
 * Class for representing an Exception occurred during the proces of image conversion
 */
public class ConversionException extends RuntimeException {

    /**
     * Create a new ImageConversion exception.
     *
     * @param message a descriptive message.
     */
    public ConversionException(final String message) {
        super(message);
    }

    /**
     * Create a new ImageConversion exception.
     *
     * @param message a descriptive message.
     * @param cause the exception trail
     */
    public ConversionException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
