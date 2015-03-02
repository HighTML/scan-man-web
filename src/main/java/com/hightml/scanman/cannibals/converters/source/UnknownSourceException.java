package com.hightml.scanman.cannibals.converters.source;

/**
 * @author Teun van Vegchel <teun.van.vegchel@ing.nl>
 */
public class UnknownSourceException extends RuntimeException {

    /**
     * Create a new unknown source exception.
     *
     * @param message A descriptive message.
     */
    public UnknownSourceException(final String message) {
        super(message);
    }

}
